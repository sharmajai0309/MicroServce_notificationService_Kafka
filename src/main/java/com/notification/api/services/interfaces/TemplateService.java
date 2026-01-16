package com.notification.api.services.interfaces;


import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;

public interface TemplateService {


    TemplateResponse createTemplate(CreateTemplateRequest request);

    FilterTemplateResponse filterTemplate(TemplateFilterRequest request);
}
