package com.api.repository;

import com.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

public Optional<User> findByEmailAndPassword(String email,String password);
}
