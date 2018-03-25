package com.xieaoran.netease.translator;

import com.xieaoran.netease.data.ShopCartItemData;
import com.xieaoran.netease.persistence.entity.ShopCartItem;

public class ShopCartItemTranslator {
    public static ShopCartItemData translate(ShopCartItem shopCartItem, boolean isSeller) {
        if (null == shopCartItem) return null;
        return ShopCartItemData.builder()
                .id(shopCartItem.getId())
                .quantity(shopCartItem.getQuantity())
                .user(isSeller ? UserTranslator.translate(shopCartItem.getUser()) : null)
                .product(ProductTranslator.translateBasic(shopCartItem.getProduct()))
                .productSnapshot(ProductSnapshotTranslator.translateBasic(shopCartItem.getProductSnapshot()))
                .snapshot(null != shopCartItem.getProductSnapshotId())
                .build();
    }
}
