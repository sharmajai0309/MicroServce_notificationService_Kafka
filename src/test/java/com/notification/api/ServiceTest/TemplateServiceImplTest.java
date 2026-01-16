package com.notification.api.ServiceTest;

import com.notification.api.models.request.SortType;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.request.SortRequest;
import com.notification.api.services.interfaces.TemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TemplateServiceImplTest {

    @Autowired
    private TemplateService templateService;

    @Test
    void shouldReturnFilteredTemplatesFromService() {


        TemplateFilterRequest request = new TemplateFilterRequest();
        request.setName("Licence");
        request.setPage(0);
        request.setSize(10);

        SortRequest sortRequest = new SortRequest();
        sortRequest.setSortKey("createdAt");
        sortRequest.setSortType(SortType.valueOf("DESC"));

        request.setSortRequest(sortRequest);


        FilterTemplateResponse response =
                templateService.filterTemplate(request);


        assertNotNull(response);


        System.out.println("Total Elements: " + response.getTotalCount());
        response.getData().forEach(dto ->
                System.out.println("Template Name: " + dto.getName())
        );
    }
}

