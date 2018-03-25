package com.xieaoran.netease.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "商品 - 基本信息")
public class ProductBasicData {
    @ApiModelProperty(value = "ID")
    private int id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "图片 URL")
    private String imageUrl;
    @ApiModelProperty(value = "价格")
    private double price;
    @ApiModelProperty(value = "是否已被删除")
    private boolean deleted;
    @ApiModelProperty(value = "买家 - 已购买的总量\n卖家 - 已售出的总量", allowEmptyValue = true)
    private Integer sumQuantity;
}
