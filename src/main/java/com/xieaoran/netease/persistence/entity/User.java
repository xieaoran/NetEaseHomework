package com.xieaoran.netease.persistence.entity;

import com.xieaoran.netease.enums.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_sequence")
    @GeneratedValue(generator = "user_id_generator")
    private int id;

    @Basic
    @Column(name = "login_name", nullable = false, unique = true)
    private String loginName;

    @Basic
    @Column(name = "nick_name")
    private String nickName;

    @Basic
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Basic
    @Column(name = "salt", nullable = false)
    private String salt;

    @Basic
    @Column
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;
}
