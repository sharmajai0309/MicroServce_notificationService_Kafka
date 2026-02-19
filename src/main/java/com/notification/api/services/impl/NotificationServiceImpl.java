package com.notification.api.services.impl;

import com.notification.api.PubSub.publisher.GenericPublisher;
import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.exception.ValidationException;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.IngestTopicDTO;
import com.notification.api.models.request.SendNotificationRequest;
import com.notification.api.services.interfaces.NotificationService;
import com.notification.api.utils.CommanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Map;


import static com.notification.api.constants.ErrorConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final TemplateDao templateDao;
    private final GenericPublisher genericPublisher;


    /**
     * @param request
     */
    @Override
    public void sendNotification(final SendNotificationRequest request) {

        Template templateFromDatabase = templateDao.findByTenantIdAndId(CommanUtils.getCurrentTenantId(), request.getTemplateId())
                .orElseThrow(() -> {
                    log.error("Template not found. tenantId={}, templateId={}, traceId={}",
                            CommanUtils.getCurrentTenantId(), request.getTemplateId(), CommanUtils.getCurrentTraceID());

                    // Publish to Audit Topic
//                     genericPublisher.sendDataToAudit();
                    return new ValidationException(Template_Not_Exists_with_Id_Error,
                            HttpStatus.BAD_REQUEST.value());
                });


        //Validate Dynamic Variables
        validateDynamicVariables(templateFromDatabase,request.getDynamicVariables());


//        Complete Notification Microservice Flow Design


        //Validation Success.... Publish Data to Ingest_Topic
        IngestTopicDTO ingestTopicDTO = new IngestTopicDTO();
        ingestTopicDTO.setRequestId(CommanUtils.getCurrentTraceID());
        ingestTopicDTO.setTemplateId(request.getTemplateId());
        ingestTopicDTO.setReceivedAt(CommanUtils.getCurrentTimeStamp());
        ingestTopicDTO.setTenantId(CommanUtils.getCurrentTenantId());
        ingestTopicDTO.setNotificationType(request.getNotificationType());
        ingestTopicDTO.setDynamicVariables(request.getDynamicVariables());

        genericPublisher.sendDataToIngest(ingestTopicDTO);
        log.info("Notification sent to ingest topic successfully. traceId={}", CommanUtils.getCurrentTraceID());

    }

    //Helper Method
    private void validateDynamicVariables(Template template,
                                          Map<String, Object> dynamicVariables) {

        Map<String, String> templateVariables = template.getTemplateVariables();

        // Case 1: Template does NOT require variables
        if (templateVariables == null || templateVariables.isEmpty()) {
            if (dynamicVariables != null && !dynamicVariables.isEmpty()) {
                throw new ValidationException(DYNAMIC_VARIABLE_NOT_REQUIRED,
                        HttpStatus.BAD_REQUEST.value());
            }
            return;
        }

        // Case 2: Template requires variables but request missing
        if (dynamicVariables == null || dynamicVariables.isEmpty()) {
            throw new ValidationException(DYNAMIC_VARIABLE_IS_REQUIRED,
                    HttpStatus.BAD_REQUEST.value());
        }

        // Case 3: Size mismatch or missing keys
        if (templateVariables.size() != dynamicVariables.size()
                || templateVariables.values().stream()
                .anyMatch(variable -> !dynamicVariables.containsKey(variable))) {

            throw new ValidationException(DYNAMIC_VARIABLES_INVALID,
                    HttpStatus.BAD_REQUEST.value());
        }
    }


}
