package com.notification.api.models.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

import static com.notification.api.constants.ErrorConstants.TEMPLATE_ID_IS_REQUIRED;

@Data
public class SendNotificationRequest {



    @NotBlank(message = TEMPLATE_ID_IS_REQUIRED)
    private String templateId;
    private Map<String,Object> dynamicVariables;
    private NotificationType notificationType;


}
