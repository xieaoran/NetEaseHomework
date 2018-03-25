package com.xieaoran.netease.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "文件 - 信息")
public class UploadItemData {
    @ApiModelProperty(value = "文件名")
    private String fileName;
    @ApiModelProperty(value = "类型")
    private String contentType;
    @ApiModelProperty(value = "大小")
    private long size;
    @ApiModelProperty(value = "完整 URL")
    private String completeUrl;
}
