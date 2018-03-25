package com.xieaoran.netease.persistence.converter;

import com.xieaoran.netease.persistence.entity.PurchasedItem;
import com.xieaoran.netease.persistence.entity.ShopCartItem;

public class ShopCartItemConverter {
    public static PurchasedItem toPurchasedItem(ShopCartItem shopCartItem) {
        PurchasedItem purchasedItem = new PurchasedItem();
        purchasedItem.setUserId(shopCartItem.getUserId());
        purchasedItem.setProductId(shopCartItem.getProductId());
        purchasedItem.setProductSnapshotId(shopCartItem.getProductSnapshotId());
        purchasedItem.setQuantity(shopCartItem.getQuantity());
        return purchasedItem;
    }
}
