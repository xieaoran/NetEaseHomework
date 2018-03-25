package com.xieaoran.netease.persistence.repository;

import com.xieaoran.netease.persistence.entity.Product;
import com.xieaoran.netease.persistence.entity.PurchasedItem;
import com.xieaoran.netease.persistence.entity.PurchasedItemPK;
import com.xieaoran.netease.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PurchasedItemRepository extends CrudRepository<PurchasedItem, PurchasedItemPK> {
    @Query(value = "SELECT IFNULL(MAX(id),0) FROM purchased_item WHERE user_id = ?1", nativeQuery = true)
    int findMaxId(int userId);

    Page<PurchasedItem> findAll(Pageable pageable);

    Page<PurchasedItem> findAllByProductDeletedIsFalse(Pageable pageable);

    Page<PurchasedItem> findAllByUser(User user, Pageable pageable);

    Page<PurchasedItem> findAllByUserAndProductDeletedIsFalse(User user, Pageable pageable);

    Collection<PurchasedItem> findAllByProductAndProductSnapshotIdIsNull(Product product);

    @Query(value = "SELECT IFNULL(SUM(quantity),0) FROM purchased_item WHERE product_id = ?1", nativeQuery = true)
    int sumQuantityByProductId(int productId);

    @Query(value = "SELECT IFNULL(SUM(quantity),0) FROM purchased_item WHERE user_id = ?1 AND product_id = ?2", nativeQuery = true)
    int sumQuantityByUserIdAndProductId(int userId, int productId);
}