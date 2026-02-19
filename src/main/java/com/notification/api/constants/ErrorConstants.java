package com.notification.api.constants;

public interface ErrorConstants {
    String Template_Already_Exist_Error = "Template Already Exist With Given Name";

    String Template_Not_Exists_with_Id_Error = "Template Not Exists With Given Id or TenantId";

    String TEMPLATE_ID_IS_REQUIRED = "Template ID Is Required";
    String PUT_CACHING_ERROR = "Error While Caching the data";
    String CACHE_PARSING_ERROR = "Error While Parsing the data";
    String TEMPLATE_VALIDATION_ERROR = "Error While Validation the data";
    String TEMPLATE_VARIABLE_ERROR = "Template Variables is Required ! And Max Size Should Be 20";
    String TEMPLATE_NAME_LIMIT_ERROR = "Name Field must not Exceed 20 letters";
    String TEMPLATE_NAME_IS_REQUIRED = "Name Field Is Required";
    String TEMPLATE_LENGTH_ERROR = "Template Length Should be Small Its A Notification System Not ad Essay Writing Page";
    String TEMPLATE_FIELD_ERROR = "Message Template Field Is required";
    String DYNAMIC_VARIABLE_IS_REQUIRED = "Dynamic Variable Is Required cannot be Null or Empty";
    String DYNAMIC_VARIABLE_NOT_REQUIRED =  "Dynamic Variable Is Not Required In This Template";
    String DYNAMIC_VARIABLES_INVALID = "Invalid Dynamic Variables";
}
