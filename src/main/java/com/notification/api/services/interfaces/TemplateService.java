package com.notification.api.services.interfaces;


import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.UpdateTemplateRequest;
import com.notification.api.models.response.DeleteResponse;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import jakarta.validation.Valid;

public interface TemplateService {


    TemplateResponse createTemplate(CreateTemplateRequest request);

    FilterTemplateResponse filterTemplate(TemplateFilterRequest request);

    TemplateResponse updateTemplate(String id,UpdateTemplateRequest request);

    DeleteResponse deleteTemplate(String id);
}
