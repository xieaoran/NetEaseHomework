package com.xieaoran.netease.translator;

import com.xieaoran.netease.data.ProductSnapshotBasicData;
import com.xieaoran.netease.data.ProductSnapshotDetailData;
import com.xieaoran.netease.persistence.entity.ProductSnapshot;

public class ProductSnapshotTranslator {
    public static ProductSnapshotBasicData translateBasic(ProductSnapshot productSnapshot) {
        if (null == productSnapshot) {
            return null;
        }
        return ProductSnapshotBasicData.builder()
                .id(productSnapshot.getId())
                .productId(productSnapshot.getProductId())
                .title(productSnapshot.getTitle())
                .imageUrl(productSnapshot.getImageUrl())
                .price(productSnapshot.getPrice())
                .timestamp(productSnapshot.getTimestamp())
                .build();
    }

    public static ProductSnapshotDetailData translateDetail(ProductSnapshot productSnapshot) {
        if (null == productSnapshot) {
            return null;
        }
        return ProductSnapshotDetailData.builder()
                .id(productSnapshot.getId())
                .productId(productSnapshot.getProductId())
                .title(productSnapshot.getTitle())
                .imageUrl(productSnapshot.getImageUrl())
                .abstractText(productSnapshot.getAbstractText())
                .mainText(productSnapshot.getMainText())
                .price(productSnapshot.getPrice())
                .timestamp(productSnapshot.getTimestamp())
                .build();
    }
}
