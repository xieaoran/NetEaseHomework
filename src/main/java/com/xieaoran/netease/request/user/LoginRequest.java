package com.xieaoran.netease.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "用户请求 - 登录")
public class LoginRequest {
    @ApiModelProperty(value = "登录名", example = "buyer")
    private String loginName;
    @ApiModelProperty(value = "密码", example = "reyub")
    private String password;
}
