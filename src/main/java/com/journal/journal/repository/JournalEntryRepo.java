package com.journal.journal.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journal.journal.entity.Journalentry;

public interface JournalEntryRepo extends MongoRepository<Journalentry, ObjectId> {
    List<Journalentry> findByUserId(ObjectId userId);

}
