package com.journal.journal.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.journal.journal.entity.Journalentry;
import com.journal.journal.repository.JournalEntryRepo;

@Component
public class JournalEntryservices {

    @Autowired
    private JournalEntryRepo jr;

    public Journalentry Createnew(Journalentry journalentry) {
        try {
            journalentry = jr.save(journalentry);
            return journalentry;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Journalentry> Getall(ObjectId user_Id) {
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
