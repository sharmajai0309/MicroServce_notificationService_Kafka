package com.notification.api.DaoTest;

import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.SortRequest;
import com.notification.api.models.request.SortType;
import com.notification.api.models.request.TemplateFilterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TemplateDaoImplTest {

    @Autowired
    private TemplateDao templateDao;

    @Test
    void shouldReturnTemplatesWhenNameMatches() {

        // given (request)
        TemplateFilterRequest request = new TemplateFilterRequest();
        request.setName("DoesNotExist");
        request.setPage(0);
        request.setSize(10);

        SortRequest sortRequest = new SortRequest();
        sortRequest.setSortKey("createdAt");
        sortRequest.setSortType(SortType.valueOf("DESC"));

        request.setSortRequest(sortRequest);

        // when
        Page<Template> result = templateDao.filterTemplate(request);

        // then
        System.out.println("Total Elements: " + result.getTotalElements());

        result.getContent().forEach(t ->
                System.out.println("Template Name: " + t.getName())
        );

        assertFalse(result.isEmpty());
    }
}

