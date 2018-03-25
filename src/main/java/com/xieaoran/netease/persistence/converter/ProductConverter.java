package com.xieaoran.netease.persistence.converter;

import com.xieaoran.netease.persistence.entity.Product;
import com.xieaoran.netease.persistence.entity.ProductSnapshot;

import java.util.Date;

public class ProductConverter {
    public static ProductSnapshot toSnapshot(Product product) {
        ProductSnapshot snapshot = new ProductSnapshot();
        snapshot.setProductId(product.getId());
        snapshot.setTitle(product.getTitle());
        snapshot.setImageUrl(product.getImageUrl());
        snapshot.setAbstractText(product.getAbstractText());
        snapshot.setMainText(product.getMainText());
        snapshot.setPrice(product.getPrice());
        snapshot.setTimestamp(new Date());
        return snapshot;
    }
}
