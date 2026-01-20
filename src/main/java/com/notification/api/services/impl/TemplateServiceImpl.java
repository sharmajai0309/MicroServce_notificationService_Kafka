package com.notification.api.services.impl;

import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.exception.ValidationException;
import com.notification.api.models.context.NotificationContext;
import com.notification.api.models.context.NotificationContextHolder;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.UpdateTemplateRequest;
import com.notification.api.models.response.DeleteResponse;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.models.response.TemplateResponseDTO;
import com.notification.api.services.interfaces.TemplateService;
import com.notification.api.utils.CommanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.notification.api.constants.ErrorConstants.Template_Already_Exist_Error;
import static com.notification.api.constants.ErrorConstants.Template_Not_Exists_with_Id_Error;
import static com.notification.api.utils.CommanUtils.getCurrentTenantId;
import static com.notification.api.utils.CommanUtils.isNotEmpty;

/**
 * The type Template service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
class TemplateServiceImpl implements TemplateService {


    private final TemplateDao templateDao;

    /**
     * create template
     *
     * @param request request
     * @return {@link TemplateResponse}
     * @see TemplateResponse
     */
    @Override
    public TemplateResponse createTemplate(CreateTemplateRequest request) {

        if (templateDao.findByTenantIdAndName(
                getCurrentTenantId(),
                request.getName()
        ).isPresent()) {
            throw new ValidationException(
                    Template_Already_Exist_Error,
                    HttpStatus.BAD_REQUEST.value()
            );
        }

        Template template = new Template();
        template.setId(CommanUtils.generateUUID());
        template.setTenantId(UUID.fromString(getCurrentTenantId()));
        BeanUtils.copyProperties(request,template);
        template.entityCreated();
        templateDao.save(template);
        return new TemplateResponse(template);
    }

    /**
     * filter template
     *
     * @param request request
     * @return {@link FilterTemplateResponse}
     * @see FilterTemplateResponse
     */
    @Override
    public FilterTemplateResponse filterTemplate(TemplateFilterRequest request) {
        Page<Template> templates = templateDao.filterTemplate(request);
        List<TemplateResponseDTO> data = templates.getContent().stream().map(TemplateResponseDTO::new).toList();
        return new FilterTemplateResponse(data,templates.hasNext(),templates.getTotalElements());
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @Override
    public TemplateResponse updateTemplate(String id, UpdateTemplateRequest request) {
        log.info("In update Service Level");
        return templateDao.findByTenantIdAndId(getCurrentTenantId(), id)
                .map(template -> {

                    if (isNotEmpty(request.getName())) {

                        templateDao.findByTenantIdAndName(
                                getCurrentTenantId(),
                                request.getName()
                        ).ifPresent(existing -> {
                            if (!existing.getId().equals(template.getId())) {
                                throw new ValidationException(
                                        Template_Already_Exist_Error,
                                        HttpStatus.BAD_REQUEST.value()
                                );
                            }
                        });

                        template.setName(request.getName());
                    }

                    //  Template variables update
                    if (isNotEmpty(request.getTemplateVariables())) {
                        template.setTemplateVariables(request.getTemplateVariables());
                    }

                    //  Message template update
                    if (isNotEmpty(request.getMessageTemplate())) {
                        template.setMessageTemplates(request.getMessageTemplate());
                    }

                    //  Save & return response
                    Template updatedTemplate = templateDao.save(template);
                    return new TemplateResponse(updatedTemplate);
                })
                .orElseThrow(() -> new ValidationException(
                        Template_Not_Exists_with_Id_Error,
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    /**
     * @param id
     * @return
     */
    @Override
    public DeleteResponse deleteTemplate(final String id) {
        return templateDao.deleteTemplateById(id);
    }
}



