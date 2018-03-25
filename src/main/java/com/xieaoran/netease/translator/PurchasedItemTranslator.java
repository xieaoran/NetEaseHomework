package com.xieaoran.netease.translator;

import com.xieaoran.netease.data.PurchasedItemData;
import com.xieaoran.netease.persistence.entity.PurchasedItem;

public class PurchasedItemTranslator {
    public static PurchasedItemData translate(PurchasedItem purchasedItem, boolean isSeller) {
        if (null == purchasedItem) return null;
        return PurchasedItemData.builder()
                .id(purchasedItem.getId())
                .quantity(purchasedItem.getQuantity())
                .timestamp(purchasedItem.getTimestamp())
                .user(isSeller ? UserTranslator.translate(purchasedItem.getUser()) : null)
                .product(ProductTranslator.translateBasic(purchasedItem.getProduct()))
                .productSnapshot(ProductSnapshotTranslator.translateBasic(purchasedItem.getProductSnapshot()))
                .snapshot(null != purchasedItem.getProductSnapshotId())
                .build();
    }
}
