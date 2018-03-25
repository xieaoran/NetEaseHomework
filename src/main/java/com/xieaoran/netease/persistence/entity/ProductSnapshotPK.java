package com.xieaoran.netease.persistence.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class ProductSnapshotPK implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Id
    @Column(name = "product_id", nullable = false)
    private int productId;
}
