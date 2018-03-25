package com.xieaoran.netease.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "已购买项目 - 信息")
public class PurchasedItemData {
    @ApiModelProperty(value = "ID")
    private int id;
    @ApiModelProperty(value = "商品数量")
    private int quantity;
    @ApiModelProperty(value = "时间戳", allowEmptyValue = true)
    private Date timestamp;
    @ApiModelProperty(value = "用户\n买家 - 不显示此项", allowEmptyValue = true)
    private UserData user;
    @ApiModelProperty(value = "商品")
    private ProductBasicData product;
    @ApiModelProperty(value = "商品快照", allowEmptyValue = true)
    private ProductSnapshotBasicData productSnapshot;
    @ApiModelProperty(value = "是否为快照")
    private boolean snapshot;
}
