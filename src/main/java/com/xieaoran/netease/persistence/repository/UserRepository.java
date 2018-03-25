package com.xieaoran.netease.persistence.repository;

import com.xieaoran.netease.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLoginName(String loginName);
}
