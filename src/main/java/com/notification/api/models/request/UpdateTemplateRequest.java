package com.notification.api.models.request;

import com.notification.api.exception.ValidationException;
import com.notification.api.utils.CommanUtils;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.notification.api.constants.ErrorConstants.*;

@Data
public class UpdateTemplateRequest {


    @Size(max = 50, message = TEMPLATE_NAME_LIMIT_ERROR)
    private String name;

    private Map<String, String> templateVariables;

    @Size(max = 10000, message = TEMPLATE_LENGTH_ERROR)
    private String messageTemplate;

    @AssertTrue(message = TEMPLATE_VARIABLE_ERROR)
    public boolean validateTemplateVariables() {

        if (templateVariables != null && templateVariables.size() > 20) {
            return false;
        }

        return true;
    }



}
