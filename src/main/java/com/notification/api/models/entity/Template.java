package com.notification.api.models.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "templates")
public class Template extends AbstractEntity{

    private UUID id;
    private String name;

    private Map<String,String> templateVariables;

    private String messageTemplates;

    private UUID tenantId;

}
