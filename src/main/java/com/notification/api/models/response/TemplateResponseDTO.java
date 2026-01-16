package com.notification.api.models.response;


import com.notification.api.models.entity.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * The type Template response dto.
 */
@Data
public class TemplateResponseDTO {


    private String id;
    private String name;
    private Map<String,String> templateVariables;

    /**
     * Instantiates a new Template response dto.
     *
     * @param template the template
     */
    public TemplateResponseDTO(Template template) {
        this.id = String.valueOf(template.getId());
        this.name = template.getName();
        this.templateVariables = template.getTemplateVariables();
    }
}
