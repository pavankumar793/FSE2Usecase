package com.fse2.usecase.userservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fse2.usecase.userservice.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User findByUsername(String username);

    User findByEmail(String email);
}
