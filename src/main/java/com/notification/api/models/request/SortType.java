package com.notification.api.models.request;

import lombok.Getter;

@Getter
public enum SortType {
    ASC("ASC"),
    DESC("DESC");

    private String value;

    SortType(final String value) {
        this.value = value;
    }
}
