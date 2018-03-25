package com.xieaoran.netease.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "product_snapshot")
@EqualsAndHashCode(exclude = {"product"})
@ToString(exclude = {"product"})
@IdClass(ProductSnapshotPK.class)
public class ProductSnapshot {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Id
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Basic
    @Column(name = "title", nullable = false, length = 80)
    private String title;

    @Basic
    @Column(name = "image_url")
    private String imageUrl;

    @Basic
    @Column(name = "abstract_text", length = 140)
    private String abstractText;

    @Basic
    @Column(name = "main_text", length = 1000)
    private String mainText;

    @Basic
    @Column(name = "price", nullable = false)
    private double price;

    @Basic
    @Column(name = "timestamp", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date timestamp;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;
}
