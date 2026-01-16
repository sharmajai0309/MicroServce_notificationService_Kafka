package com.notification.api.models.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The type Filter template response.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FilterTemplateResponse extends BaseTemplateResponse<TemplateResponseDTO,Long>{

    /**
     * Instantiates a new Filter template response.
     *
     * @param list           the list
     * @param hasMoreElement the has more element
     * @param totalCount     the total count
     */
    public FilterTemplateResponse(List<TemplateResponseDTO> list,
                                  boolean hasMoreElement,
                                  Long totalCount){
        setData(list);
        setTotalCount(totalCount);
        setHasMoreElement(hasMoreElement);

    }


}
