package com.notification.api.dao.impl;

import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.dao.repositories.TemplateRepository;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.response.DeleteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.notification.api.utils.CommanUtils.getCurrentTenantId;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateDaoImpl implements TemplateDao {

     private final TemplateRepository templateRepository;

     private final MongoTemplate  mongoTemplate;


    @Override
    public Optional<Template> findByTenantIdAndName(final String tenantId, final String name) {
        return templateRepository.findByNameIgnoreCaseAndTenantId(name, UUID.fromString(tenantId));
    }


    @Override
    public Optional<Template> findByTenantIdAndId(final String tenantId, final String id) {
        return templateRepository.findByTenantIdAndId(UUID.fromString(tenantId),
                UUID.fromString(id));
    }



    @Override
    public Template save(final Template template) {
        log.info("In Dao Level");
        return templateRepository.save(template);
    }

    /**
     * @param request
     * @return
     */
    @Override
    public Page<Template> filterTemplate(TemplateFilterRequest request) {

        log.info("In Dao level");
        Query query = new Query();


        if (request.getName() != null && !request.getName().isBlank()) {
            query.addCriteria(
                    Criteria.where("name")
                            .regex(request.getName().trim(), "i")
            );
        }


        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 10;

        Pageable pageable = PageRequest.of(page, size);


        if (request.getSortRequest() != null
                && request.getSortRequest().getSortKey() != null
                && request.getSortRequest().getSortType() != null) {

            Sort.Direction direction =
                    Sort.Direction.fromString(
                            request.getSortRequest().getSortType().getValue()
                    );

            query.with(
                    Sort.by(direction, request.getSortRequest().getSortKey())
            );
        }
        long total = mongoTemplate.count(query, Template.class);
        query.with(pageable);
        List<Template> data =
                mongoTemplate.find(query, Template.class);
        return new PageImpl<>(data, pageable, total);

    }

    /**
     * @param id
     * @return
     */
    @Override
    public DeleteResponse deleteTemplateById(final String id) {
        Optional<Template> byTenantIdAndId = findByTenantIdAndId(
                getCurrentTenantId(),
                id
        );
        DeleteResponse deleteResponse = new DeleteResponse();
        if (byTenantIdAndId.isEmpty()) {
            deleteResponse.setMessage("Template with id " + id + " not found");
        } else {
            templateRepository.deleteById(UUID.fromString(id));
            deleteResponse.setMessage("Template with id " + id + " deleted");
        }
        return deleteResponse;
    }


}



