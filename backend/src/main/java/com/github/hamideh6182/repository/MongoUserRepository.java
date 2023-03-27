package com.github.hamideh6182.repository;

import com.github.hamideh6182.model.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoUserRepository extends MongoRepository<MongoUser, String> {
    Optional<MongoUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
