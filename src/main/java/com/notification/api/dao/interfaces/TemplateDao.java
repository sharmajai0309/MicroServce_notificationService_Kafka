package com.notification.api.dao.interfaces;


import com.notification.api.models.entity.Template;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.response.DeleteResponse;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface TemplateDao {

    public Optional<Template> findByTenantIdAndName(final String tenantId, final String name);


    Optional<Template> findByTenantIdAndId(final String tenantId, final String id);

    Template save(Template template);

    Page<Template> filterTemplate(TemplateFilterRequest request);

    DeleteResponse deleteTemplateById(String id);

}
