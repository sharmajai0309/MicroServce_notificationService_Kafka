package com.notification.api.dao.repositories;

import com.notification.api.models.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends MongoRepository<Template, UUID> {

    /**
     * find by name ignore case and tenant id
     *
     * @param name name
     * @param templateId templateId
     * @return {@link Optional}
     * @see Optional
     * @see Template
     */
    Optional<Template> findByNameIgnoreCaseAndTenantId(String name , UUID templateId);


    /**
     * find by id and tenant id
     *
     * @param id id
     * @param templateId templateId
     * @return {@link Optional}
     * @see Optional
     * @see Template
     */
    Optional<Template> findByTenantIdAndId(UUID tenantId, UUID id);

    



}
