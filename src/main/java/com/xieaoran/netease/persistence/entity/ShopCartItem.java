package com.xieaoran.netease.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shop_cart_item")
@EqualsAndHashCode(exclude = {"user", "product", "productSnapshot"})
@ToString(exclude = {"user", "product", "productSnapshot"})
@IdClass(ShopCartItemPK.class)
public class ShopCartItem {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Basic
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Basic
    @Column(name = "product_snapshot_id")
    private Integer productSnapshotId;

    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumns({
            @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false),
            @JoinColumn(name = "product_snapshot_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private ProductSnapshot productSnapshot;
}
