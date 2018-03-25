package com.xieaoran.netease.data;

import com.xieaoran.netease.enums.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "用户 - 信息")
public class UserData {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "登录名")
    private String loginName;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "角色")
    private UserRole role;
}
