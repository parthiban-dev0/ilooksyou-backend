package com.ilooksyou.repository;

import com.ilooksyou.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'email': ?0}")
    public Optional<User> findByEmail(@Param("email") String email);
}
