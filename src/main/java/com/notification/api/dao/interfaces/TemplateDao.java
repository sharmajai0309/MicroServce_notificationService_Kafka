package com.notification.api.dao.interfaces;


import com.notification.api.models.entity.Template;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.response.DeleteResponse;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

/**
 *  template dao
 *
 */
public interface TemplateDao {

    /**
     * find by tenant id and name
     *
     * @param tenantId tenantId
     * @param name name
     * @return {@link Optional}
     * @see Optional
     * @see Template
     */
    public Optional<Template> findByTenantIdAndName(final String tenantId, final String name);


    /**
     * find by tenant id and id
     *
     * @param tenantId tenantId
     * @param id id
     * @return {@link Optional}
     * @see Optional
     * @see Template
     */
    Optional<Template> findByTenantIdAndId(final String tenantId, final String templateId);

    /**
     * save
     *
     * @param template template
     * @return {@link Template}
     * @see Template
     */
    Template save(Template template);

    /**
     * filter template
     *
     * @param request request
     * @return {@link Page}
     * @see Page
     * @see Template
     */
    Page<Template> filterTemplate(TemplateFilterRequest request);

    /**
     * delete template by id
     *
     * @param id id
     * @return {@link DeleteResponse}
     * @see DeleteResponse
     */
    DeleteResponse deleteTemplateById(String id);

}
