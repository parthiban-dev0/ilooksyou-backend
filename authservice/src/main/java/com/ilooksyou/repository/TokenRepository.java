package com.ilooksyou.repository;

import com.ilooksyou.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token,String> {

    @Query("{'refreshToken': ?0}")
    public Optional<Token> findByRefreshToken(@Param("refreshToken") String refreshToken);
}
