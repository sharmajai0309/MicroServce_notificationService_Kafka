package com.notification.api.dao.repositories;

import com.notification.api.models.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends MongoRepository<Template, UUID> {
    Optional<Template> findByNameIgnoreCaseAndTenantId(String name , UUID templateId);

    



}
