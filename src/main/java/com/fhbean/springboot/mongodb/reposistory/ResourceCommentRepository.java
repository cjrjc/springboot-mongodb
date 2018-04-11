package com.fhbean.springboot.mongodb.reposistory;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fhbean.springboot.mongodb.entity.ResourceComment;

public interface ResourceCommentRepository extends MongoRepository<ResourceComment, String> {

}
