package com.journal.journal.controller;

import java.time.LocalDateTime;
import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Get all journal entries for a specific user
    @GetMapping("/{user_id}")
    public ResponseEntity<List<Journalentry>> getAllEntries(@PathVariable ObjectId user_id) {
        try {
            List<Journalentry> entries = jsr.Getall(user_id);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a new journal entry for a specific user
    @PostMapping("/{u_id}")
    public ResponseEntity<Journalentry> createEntry(@PathVariable String u_id, @RequestBody Journalentry entry) {
        try {
            entry.setDate(LocalDateTime.now());
            ObjectId userId = new ObjectId(u_id);
            entry.setUserId(userId);
            Journalentry res = jsr.Createnew(entry);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/entry/{id}")
    public ResponseEntity<Journalentry> getentry(@PathVariable ObjectId id) {
        Optional<Journalentry> entry = jsr.getbyid(id);
        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/entry/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable ObjectId id) {
        try {
            return new ResponseEntity<>(jsr.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/entry/{id}")
    public ResponseEntity<Journalentry> upddate(@RequestBody Journalentry newEntry, @PathVariable ObjectId id) {
        try {
            Journalentry old = jsr.getbyid(id).orElse(null);
            if (old != null) {
                old.setContent(
                        newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent()
                                : old.getContent());
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
                        : old.getTitle());
                jsr.Createnew(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
