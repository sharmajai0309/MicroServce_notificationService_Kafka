package com.notification.api.models.request;

import com.notification.api.models.entity.AbstractEntity;
import lombok.Data;

@Data
public class BaseSearchDTO<T> {

    private Integer page = 0;
    private Integer size = 10;
    private SortRequest sortRequest;
}
