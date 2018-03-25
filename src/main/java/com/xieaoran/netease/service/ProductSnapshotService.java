package com.xieaoran.netease.service;

import com.xieaoran.netease.data.ProductSnapshotBasicData;
import com.xieaoran.netease.data.ProductSnapshotDetailData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSnapshotService {
    Page<ProductSnapshotBasicData> list(int productId, Pageable pageable);

    ProductSnapshotDetailData detail(int productId, int id);
}
