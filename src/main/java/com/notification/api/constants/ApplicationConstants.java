package com.notification.api.constants;

public interface ApplicationConstants {

    // In Interface whatever we define variables it is by default static and final

    String X_TENANT_ID = "x-tenant-id";

    String X_REQUEST_ID = "x-request-id";

    String TEMPLATE_REDIS_PREFIX = "template.";

    String REDIS_LOOKUP_BY_NAME = "BY_NAME:";
    String REDIS_LOOKUP_BY_ID = "BY_ID:";

    String TEMPLATE_NOT_FOUND_BY_ID = "NOT_FOUND_BY_ID:";
    String TEMPLATE_DELETED = "TEMPLATED_DELETED:";

}
