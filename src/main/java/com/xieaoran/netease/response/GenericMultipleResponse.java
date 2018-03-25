package com.xieaoran.netease.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "通用响应 - 数据集合")
public class GenericMultipleResponse<T> extends GenericResponse {
    @ApiModelProperty(value = "数据集合")
    private Collection<T> items = new ArrayList<>();

    public GenericMultipleResponse(Iterable<T> iterable) {
        for (T item : iterable) {
            items.add(item);
        }
    }
}
