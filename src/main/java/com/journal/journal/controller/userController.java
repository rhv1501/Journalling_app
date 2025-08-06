package com.journal.journal.controller;

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

import com.journal.journal.entity.UserEntity;
import com.journal.journal.services.userServices;

@RestController
@RequestMapping("/api/user")
public class userController {
    @Autowired
    userServices userservice;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getaAllUsers() {
        try {
            List<UserEntity> entries = userservice.Getall();
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createEntry(@RequestBody UserEntity user) {
        try {
            UserEntity res = userservice.Createnew(user);
            return new ResponseEntity<>("User created", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user exists"); // 409 Conflict for already exists
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getentry(@PathVariable ObjectId id) {
        // return jsr.getbyid(id).orElse(null);
        Optional<UserEntity> entry = userservice.getbyid(id);
        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable ObjectId id) {
        try {
            return new ResponseEntity<>(userservice.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> update(@RequestBody UserEntity newUser, @PathVariable ObjectId id) {
        try {
            UserEntity old = userservice.getbyid(id).orElse(null);
            if (old != null) {
                if (newUser.getUsername() != null && !newUser.getUsername().isEmpty()) {
                    old.setUsername(newUser.getUsername());
                }
                if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
                    old.setPassword(newUser.getPassword());
                }
                UserEntity updated = userservice.Createnew(old);
                return new ResponseEntity<>(updated, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
