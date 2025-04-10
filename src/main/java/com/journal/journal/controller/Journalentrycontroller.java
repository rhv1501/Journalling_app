package com.journal.journal.controller;

import java.time.LocalDateTime;
import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.journal.entity.Journalentry;
import com.journal.journal.services.JournalEntryservices;

@RestController
@RequestMapping("/api/journal")
public class Journalentrycontroller {
    @Autowired
    JournalEntryservices jsr;

    @GetMapping
    public List<Journalentry> getAllEntries() {
        return jsr.Getall();

    }

    @PostMapping
    public Journalentry createEntry(@RequestBody Journalentry entry) {
        entry.setDate(LocalDateTime.now());
        Journalentry res = jsr.Createnew(entry);
        return res;
    }

    @GetMapping("/{id}")
    public Journalentry getentry(@PathVariable ObjectId id) {
        return jsr.getbyid(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public String deleteEntry(@PathVariable ObjectId id) {
        return jsr.delete(id);
    }

    @PutMapping("/{id}")
    public Journalentry upddate(@RequestBody Journalentry newEntry, @PathVariable ObjectId id) {
        Journalentry old = jsr.getbyid(id).orElse(null);
        if (old != null) {
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent()
                    : old.getContent());
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
                    : old.getTitle());
        }
        jsr.Createnew(old);
        return old;
    }

}
