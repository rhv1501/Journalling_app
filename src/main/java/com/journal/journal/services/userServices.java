package com.journal.journal.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.journal.journal.entity.UserEntity;
import com.journal.journal.repository.userRepo;

@Component
public class userServices {

    @Autowired
    private userRepo userRepo;

    public UserEntity Createnew(UserEntity user) {
        if (userRepo.findAll().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            throw new IllegalArgumentException("User already exists with username: " + user.getUsername());
        }
        try {
            return userRepo.save(user);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public List<UserEntity> Getall() {
        return userRepo.findAll();
    }

    public String delete(ObjectId id) {
        userRepo.deleteById(id);
        return "Deleted successfully";
    }

    public Optional<UserEntity> getbyid(ObjectId id) {
        return userRepo.findById(id);
    }

}
