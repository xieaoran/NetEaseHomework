package com.xieaoran.netease.service;

import com.xieaoran.netease.data.ProductSnapshotBasicData;
import com.xieaoran.netease.data.ProductSnapshotDetailData;
import com.xieaoran.netease.exception.AppException;
import com.xieaoran.netease.exception.ErrorCode;
import com.xieaoran.netease.persistence.entity.ProductSnapshot;
import com.xieaoran.netease.persistence.repository.ProductSnapshotRepository;
import com.xieaoran.netease.translator.ProductSnapshotTranslator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductSnapshotServiceImpl implements ProductSnapshotService {
    private ProductSnapshotRepository productSnapshotRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSnapshotBasicData> list(int productId, Pageable pageable) {
        Page<ProductSnapshot> productSnapshots =
                productSnapshotRepository.findAllByProductId(productId, pageable);
        return productSnapshots.map(ProductSnapshotTranslator::translateBasic);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductSnapshotDetailData detail(int productId, int id) {
        Optional<ProductSnapshot> productSnapshotOptional =
                productSnapshotRepository.findByProductIdAndId(productId, id);
        if (!productSnapshotOptional.isPresent())
            throw new AppException(ErrorCode.PRODUCT_SNAPSHOT_NOT_EXIST);
        ProductSnapshot productSnapshot = productSnapshotOptional.get();
        return ProductSnapshotTranslator.translateDetail(productSnapshot);
    }
}
