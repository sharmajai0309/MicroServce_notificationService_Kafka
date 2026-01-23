package com.notification.api.services.impl;

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
import org.springframework.web.bind.annotation.RestController;

import javax.management.Notification;
import java.util.Optional;

import static com.notification.api.constants.ErrorConstants.Template_Not_Exists_with_Id_Error;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final TemplateDao templateDao;

    /**
     * @param request
     */
    @Override
    public void sendNotification(final SendNotificationRequest request) {

        Optional<Template> byTenantIdAndId = templateDao.findByTenantIdAndId(CommanUtils.getCurrentTenantId(), request.getTemplateId());

        if(byTenantIdAndId.isEmpty()){

            log.error("Template not found Sending to Audit Topic");

            // Validation Failed.... Publish Data to Audit_Topic

            throw new ValidationException(Template_Not_Exists_with_Id_Error, HttpStatus.BAD_REQUEST.value());
        }

        //Validation Success.... Publish Data to Ingest_Topic
        IngestTopicDTO ingestTopicDTO = new IngestTopicDTO();
        ingestTopicDTO.setRequestId(CommanUtils.getCurrentTranceID());
        ingestTopicDTO.setTemplateId(CommanUtils.getCurrentTenantId());
        ingestTopicDTO.setReceivedAt(CommanUtils.getCurrentTimeStamp());
        ingestTopicDTO.setTenantId(request.getTemplateId());
        ingestTopicDTO.setNotificationType(request.getNotificationType());
        ingestTopicDTO.setDynamicVariables(request.getDynamicVariables());
    }
}
