package com.xieaoran.netease.persistence.repository;

import com.xieaoran.netease.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByDeletedIsFalse(Pageable pageable);

    @Query("SELECT product FROM Product AS product WHERE product.id NOT IN " +
            "(SELECT purchased_item.productId FROM PurchasedItem AS purchased_item " +
            "WHERE purchased_item.userId  = ?1)")
    Page<Product> findByUserNotPurchased(int userId, Pageable pageable);

    @Query("SELECT product FROM Product AS product WHERE product.deleted = false AND " +
            "product.id NOT IN " +
            "(SELECT purchased_item.productId FROM PurchasedItem AS purchased_item " +
            "WHERE purchased_item.userId  = ?1)")
    Page<Product> findByUserNotPurchasedAndDeletedIsFalse(int userId, Pageable pageable);
}
