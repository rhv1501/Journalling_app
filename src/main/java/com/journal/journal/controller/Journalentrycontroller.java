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

    // @GetMapping("/{user_id}")
    @GetMapping
    public ResponseEntity<List<Journalentry>> getAllEntries(@PathVariable ObjectId user_id) {
        try {
            List<Journalentry> entries = jsr.Getall(user_id);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // (/{user_id})
    @PostMapping
    public ResponseEntity<Journalentry> createEntry(@PathVariable ObjectId u_id, @RequestBody Journalentry entry) {
        try {
            entry.setDate(LocalDateTime.now());
            entry.setUserId(u_id);
            Journalentry res = jsr.Createnew(entry);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Journalentry> getentry(@PathVariable ObjectId id) {
        // return jsr.getbyid(id).orElse(null);
        Optional<Journalentry> entry = jsr.getbyid(id);
        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable ObjectId id) {
        try {
            return new ResponseEntity<>(jsr.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
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
