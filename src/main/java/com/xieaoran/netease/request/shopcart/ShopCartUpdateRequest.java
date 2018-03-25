package com.xieaoran.netease.request.shopcart;

import com.xieaoran.netease.persistence.entity.ShopCartItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "购物车项目请求 - 新增/更新")
public class ShopCartUpdateRequest {
    @ApiModelProperty(value = "商品 ID")
    private int productId;
    @ApiModelProperty(value = "商品数量")
    private int quantity;

    public boolean checkUpdate(ShopCartItem shopCartItem) {
        boolean equals = null == shopCartItem.getProductSnapshotId() &&
                this.getProductId() == shopCartItem.getProductId() &&
                this.getQuantity() == shopCartItem.getQuantity();
        return !equals;
    }

    public void update(ShopCartItem shopCartItem) {
        shopCartItem.setProductSnapshotId(null);
        shopCartItem.setProductId(this.getProductId());
        shopCartItem.setQuantity(this.getQuantity());
    }
}
