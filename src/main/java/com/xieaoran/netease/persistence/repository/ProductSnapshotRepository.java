package com.xieaoran.netease.persistence.repository;

import com.xieaoran.netease.persistence.entity.ProductSnapshot;
import com.xieaoran.netease.persistence.entity.ProductSnapshotPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSnapshotRepository extends CrudRepository<ProductSnapshot, ProductSnapshotPK> {
    @Query(value = "SELECT IFNULL(MAX(id),0) FROM product_snapshot WHERE product_id = ?1", nativeQuery = true)
    int findMaxId(int productId);

    Page<ProductSnapshot> findAllByProductId(int productId, Pageable pageable);

    Optional<ProductSnapshot> findByProductIdAndId(int productId, int id);
}
