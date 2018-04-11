package com.fhbean.springboot.mongodb.reposistory;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fhbean.springboot.mongodb.entity.User;

public interface UserRepository extends MongoRepository<User, Long> {

	User findByUsername(String username);

}
