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
@ApiModel(description = "商品快照 - 详细信息")
public class ProductSnapshotDetailData {
    @ApiModelProperty(value = "ID")
    private int id;
    @ApiModelProperty(value = "商品 ID")
    private int productId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "图片 URL")
    private String imageUrl;
    @ApiModelProperty(value = "摘要")
    private String abstractText;
    @ApiModelProperty(value = "正文")
    private String mainText;
    @ApiModelProperty(value = "价格")
    private double price;
    @ApiModelProperty(value = "时间戳")
    private Date timestamp;
}
