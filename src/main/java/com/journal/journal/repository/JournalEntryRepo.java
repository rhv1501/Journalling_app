package com.journal.journal.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journal.journal.entity.Journalentry;

public interface JournalEntryRepo extends MongoRepository<Journalentry, ObjectId> {
    
}
