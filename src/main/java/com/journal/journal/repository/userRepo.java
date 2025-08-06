package com.journal.journal.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journal.journal.entity.UserEntity;

public interface userRepo extends MongoRepository<UserEntity, ObjectId> {
    
}
