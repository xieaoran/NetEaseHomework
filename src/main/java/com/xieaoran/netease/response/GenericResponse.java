package com.xieaoran.netease.response;

import com.xieaoran.netease.exception.AppException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "通用响应")
public abstract class GenericResponse {
    @ApiModelProperty(value = "返回代码")
    private int code = 0;
    @ApiModelProperty(value = "错误信息", allowEmptyValue = true)
    private String message;

    public void setException(Exception ex) {
        if (ex instanceof AppException) {
            AppException appEx = (AppException) ex;
            this.setCode(appEx.getErrorCode());
        } else {
            this.setCode(-Short.MAX_VALUE & 0x0FFFF);
        }
        this.setMessage(ex.getMessage());
    }
}
