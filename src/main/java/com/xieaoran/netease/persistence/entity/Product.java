package com.xieaoran.netease.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
@EqualsAndHashCode
@ToString
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_sequence")
    @GeneratedValue(generator = "product_id_generator")
    private int id;

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
    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
