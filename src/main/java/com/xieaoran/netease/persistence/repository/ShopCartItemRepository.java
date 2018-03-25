package com.xieaoran.netease.persistence.repository;

import com.xieaoran.netease.persistence.entity.Product;
import com.xieaoran.netease.persistence.entity.ShopCartItem;
import com.xieaoran.netease.persistence.entity.ShopCartItemPK;
import com.xieaoran.netease.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ShopCartItemRepository extends CrudRepository<ShopCartItem, ShopCartItemPK> {
    @Query(value = "SELECT IFNULL(MAX(id),0) FROM shop_cart_item WHERE user_id = ?1", nativeQuery = true)
    int findMaxId(int userId);

    Page<ShopCartItem> findAll(Pageable pageable);

    Collection<ShopCartItem> findAllByUser(User user);

    Page<ShopCartItem> findAllByUser(User user, Pageable pageable);

    Optional<ShopCartItem> findByUserAndProduct(User user, Product product);

    Optional<ShopCartItem> findByUserAndProductId(User user, int productId);

    Collection<ShopCartItem> findAllByProductAndProductSnapshotIdIsNull(Product product);
}
