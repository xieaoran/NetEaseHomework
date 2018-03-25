package com.xieaoran.netease.translator;

import com.xieaoran.netease.data.ProductBasicData;
import com.xieaoran.netease.data.ProductDetailData;
import com.xieaoran.netease.persistence.entity.Product;

public class ProductTranslator {
    public static ProductBasicData translateBasic(Product product) {
        return translateBasic(product, null);
    }

    public static ProductBasicData translateBasic(Product product, Integer sumQuantity) {
        if (null == product) {
            return null;
        }
        return ProductBasicData.builder()
                .id(product.getId())
                .title(product.getTitle())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice())
                .deleted(product.isDeleted())
                .sumQuantity(sumQuantity)
                .build();
    }

    public static ProductDetailData translateDetail(Product product) {
        return translateDetail(product, null);
    }

    public static ProductDetailData translateDetail(Product product, Integer sumQuantity) {
        if (null == product) {
            return null;
        }
        return ProductDetailData.builder()
                .id(product.getId())
                .title(product.getTitle())
                .imageUrl(product.getImageUrl())
                .abstractText(product.getAbstractText())
                .mainText(product.getMainText())
                .price(product.getPrice())
                .deleted(product.isDeleted())
                .sumQuantity(sumQuantity)
                .build();
    }
}
