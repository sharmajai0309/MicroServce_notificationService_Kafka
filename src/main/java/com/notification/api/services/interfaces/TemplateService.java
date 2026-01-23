package com.notification.api.services.interfaces;


import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.UpdateTemplateRequest;
import com.notification.api.models.response.DeleteResponse;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import jakarta.validation.Valid;

public interface TemplateService {


    /**
     * create template
     *
     * @param request request
     * @return {@link TemplateResponse}
     * @see TemplateResponse
     */
    TemplateResponse createTemplate(CreateTemplateRequest request);

    /**
     * filter template
     *
     * @param request request
     * @return {@link FilterTemplateResponse}
     * @see FilterTemplateResponse
     */
    FilterTemplateResponse filterTemplate(TemplateFilterRequest request);

    /**
     * update template
     *
     * @param id id
     * @param request request
     * @return {@link TemplateResponse}
     * @see TemplateResponse
     */
    TemplateResponse updateTemplate(String id,UpdateTemplateRequest request);

    /**
     * delete template
     *
     * @param id id
     * @return {@link DeleteResponse}
     * @see DeleteResponse
     */
    DeleteResponse deleteTemplate(String id);
}
