package com.notification.api.models.response;


import com.notification.api.models.entity.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateResponse {

    private String id;
    private String name;

    public TemplateResponse(final Template template) {
        this.id = template.getId().toString();
        this.name = template.getName();
    }
}
