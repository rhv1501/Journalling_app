package com.journal.journal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.journal.journal.entity.Journalentry;
import com.journal.journal.entity.UserEntity;
import com.journal.journal.repository.JournalEntryRepo;
import com.journal.journal.repository.userRepo;

@Component
public class JournalEntryservices {

    @Autowired
    private JournalEntryRepo jr;
    @Autowired
    private userRepo ur;

    public Journalentry Createnew(Journalentry journalentry) {
        return jr.save(journalentry); // Let database exceptions bubble up
    }

    // SERVICE - Throw meaningful exceptions, don't catch them
    public List<Journalentry> Getall(ObjectId user_Id) {
        Optional<UserEntity> user = ur.findById(user_Id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + user_Id);
        }
        return jr.findByUserId(user_Id);
    }

    public String delete(ObjectId id) {
        jr.deleteById(id);
        return "Deleted successfully";
    }

    public Optional<Journalentry> getbyid(ObjectId id) {
        return jr.findById(id);
    }

}
