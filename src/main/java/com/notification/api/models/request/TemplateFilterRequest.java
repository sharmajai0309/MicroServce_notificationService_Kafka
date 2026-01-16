package com.notification.api.models.request;

import com.notification.api.models.entity.Template;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateFilterRequest extends BaseSearchDTO<Template> {
    private String name;
}
