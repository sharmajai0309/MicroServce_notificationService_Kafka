package com.notification.api.controller.Template;

import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.UpdateTemplateRequest;
import com.notification.api.models.response.DeleteResponse;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.services.interfaces.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class TemplateController {

    /**
     * template service
     *
     */
    private final TemplateService templateService;


    /**
     * create template
     *
     * @param request request
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see TemplateResponse
     */
    @PostMapping
    public ResponseEntity<TemplateResponse> createTemplate(@Valid @RequestBody CreateTemplateRequest request) {
        TemplateResponse templateResponse = templateService.createTemplate(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(templateResponse);

    }



    /**
     * filter template
     *
     * @param request request
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see FilterTemplateResponse
     */
    @PostMapping("/filter")
    public ResponseEntity<FilterTemplateResponse> filterTemplate(@RequestBody TemplateFilterRequest request) {
        FilterTemplateResponse filterTemplateResponse = templateService.filterTemplate(request);
        return ResponseEntity.status(HttpStatus.OK).body(filterTemplateResponse);
    }

    /**
     * update template
     *
     * @param id id
     * @param request request
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see TemplateResponse
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TemplateResponse>updateTemplate(@PathVariable String id,@RequestBody UpdateTemplateRequest request){
        log.info("In update controller Level");
       return ResponseEntity.ok(templateService.updateTemplate(id, request));
    }


    /**
     * delete template
     *
     * @param id id
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see DeleteResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse>deleteTemplate(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(templateService.deleteTemplate(id));
    }





}
