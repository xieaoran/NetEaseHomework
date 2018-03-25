package com.xieaoran.netease.service;

import com.xieaoran.netease.data.ProductBasicData;
import com.xieaoran.netease.data.ProductDetailData;
import com.xieaoran.netease.request.product.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface ProductService {
    Page<ProductBasicData> list(boolean includeDeleted, Pageable pageable, HttpSession session);

    Page<ProductBasicData> listNew(boolean includeDeleted, Pageable pageable, HttpSession session);

    ProductDetailData detail(int id, HttpSession session);

    ProductDetailData update(ProductUpdateRequest request, HttpSession session);

    ProductBasicData delete(int id, HttpSession session);
}
