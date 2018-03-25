package com.xieaoran.netease.request.product;

import com.xieaoran.netease.persistence.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "商品请求 - 新增/更新")
public class ProductUpdateRequest {
    @ApiModelProperty(value = "ID\n若 null 则为新增", allowEmptyValue = true)
    private Integer id;
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

    public boolean checkUpdate(Product product) {
        boolean equals = this.getTitle().equals(product.getTitle()) &&
                this.getImageUrl().equals(product.getImageUrl()) &&
                this.getAbstractText().equals(product.getAbstractText()) &&
                this.getMainText().equals(product.getMainText()) &&
                this.getPrice() == product.getPrice();
        return !equals;
    }

    public void update(Product product) {
        product.setTitle(this.getTitle());
        product.setImageUrl(this.getImageUrl());
        product.setAbstractText(this.getAbstractText());
        product.setMainText(this.getMainText());
        product.setPrice(this.getPrice());
        product.setDeleted(false);
    }
}
