package com.xieaoran.netease.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "通用响应 - 单个数据")
public class GenericSingleResponse<T> extends GenericResponse {
    @ApiModelProperty("数据")
    private T item;

    public GenericSingleResponse(T item) {
        this.setItem(item);
    }
}
