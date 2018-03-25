package com.xieaoran.netease.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "购物车项目 - 信息")
public class ShopCartItemData {
    @ApiModelProperty(value = "ID")
    private int id;
    @ApiModelProperty(value = "商品数量")
    private int quantity;
    @ApiModelProperty(value = "用户\n买家 - 不显示此项", allowEmptyValue = true)
    private UserData user;
    @ApiModelProperty(value = "商品")
    private ProductBasicData product;
    @ApiModelProperty(value = "商品快照", allowEmptyValue = true)
    private ProductSnapshotBasicData productSnapshot;
    @ApiModelProperty(value = "是否为快照")
    private boolean snapshot;
}
