package com.notification.api.dao.impl;

import com.notification.api.dao.interfaces.CacheService;
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

import static com.notification.api.constants.ApplicationConstants.TEMPLATE_DELETED;
import static com.notification.api.constants.ApplicationConstants.TEMPLATE_NOT_FOUND_BY_ID;
import static com.notification.api.utils.CommanUtils.getCurrentTenantId;

/**
 *  template dao impl
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
class TemplateDaoImpl implements TemplateDao {

     private final TemplateRepository templateRepository;
     private final MongoTemplate  mongoTemplate;
     private final CacheService cacheService;


    /**
     * find by tenant id and name
     *
     * @param tenantId tenantId
     * @param name name
     * @return {@link Optional}
     * @see Optional
     * @see Template
     */
    @Override
    public Optional<Template> findByTenantIdAndName(final String tenantId, final String templateName) {

        Optional<Template> cachedTemplate = cacheService.getByName(tenantId, templateName, Template.class);
        if(cachedTemplate.isPresent()) {
            return cachedTemplate;
        }
        Optional<Template> dbTemplate = templateRepository.findByNameIgnoreCaseAndTenantId(templateName, UUID.fromString(tenantId));
        if(dbTemplate.isPresent()){
            Template template = dbTemplate.get();
            cacheService.putByName(tenantId,templateName,template);
        }
        return dbTemplate;

//        Functional Style
//        return cacheService.getByName(tenantId, templateName, Template.class)
//                .or(() ->
//                        templateRepository
//                                .findByNameIgnoreCaseAndTenantId(
//                                        templateName,
//                                        UUID.fromString(tenantId)
//                                )
//                                .map(template -> {
//                                    cacheService.putByName(tenantId, templateName, template);
//                                    return template;
//                                })
//                );



    }


    /**
     * find by tenant id and id
     *
     * @param tenantId tenantId
     * @param id id
     * @return {@link Optional}
     * @see Optional
     * @see Template
     */
    @Override
    public Optional<Template> findByTenantIdAndId(final String tenantId, final String id) {
        return cacheService.getByID(tenantId, id, Template.class).or(() ->
                templateRepository.findByTenantIdAndId(UUID.fromString(tenantId), UUID.fromString(id))
                        .map(template -> {
                            cacheService.putBYid(tenantId,id,template);
                            return template;
                        })
        );
    }



    /**
     * save
     *
     * @param template template
     * @return {@link Template}
     * @see Template
     */
    @Override
    public Template save(final Template template) {
        log.info("In Dao Level");
        Template saved = templateRepository.save(template);
        cacheService.putByName(
                getCurrentTenantId(),
                saved.getName(),
                saved
        );
        cacheService.putBYid(
                getCurrentTenantId(),
                saved.getId().toString(),
                saved
        );
        return saved;
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
        String tenantId = getCurrentTenantId();

        Optional<Template> byTenantIdAndId = findByTenantIdAndId(
                tenantId,
                id
        );
        DeleteResponse deleteResponse = new DeleteResponse();
        if (byTenantIdAndId.isEmpty()) {
            deleteResponse.setMessage(TEMPLATE_NOT_FOUND_BY_ID + id);
        } else {
            Template template = byTenantIdAndId.get();
            /*
            clearing Database
             */
            templateRepository.deleteById(UUID.fromString(id));
            deleteResponse.setMessage(TEMPLATE_DELETED + id);
            /*
            clearing cache
            1.By name
            2.By Id
             */
            cacheService.deleteByName(tenantId, template.getName());
            cacheService.deleteById(tenantId,id);
            deleteResponse.setMessage(
                    "Template with id " + id + " deleted and cache data is also cleared"
            );
        }
        return deleteResponse;
    }


}



