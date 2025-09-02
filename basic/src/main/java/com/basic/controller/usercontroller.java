package com.basic.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.basic.entity.user;
import com.basic.repository.userRepo;


@RestController

public class usercontroller {

    @Autowired
    private userRepo userRepo;

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<user>> getUsers() {
        List<user> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }

    // Get user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<user> getUserById(@PathVariable Long id) {
        Optional<user> userOpt = userRepo.findById(id);
        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new user
    @PostMapping("/users")
    public ResponseEntity<user> createUser(@RequestBody user userObj) {
        user savedUser = userRepo.save(userObj);
        return ResponseEntity.ok(savedUser);
    }

    // Update user
    @PutMapping("/users/{id}")
    public ResponseEntity<user> updateUser(@PathVariable Long id, @RequestBody user userObj) {
        Optional<user> userOpt = userRepo.findById(id);
        if (userOpt.isPresent()) {
            user existingUser = userOpt.get();
            existingUser.setName(userObj.getName());
            existingUser.setAge(userObj.getAge());
            existingUser.setEmail(userObj.getEmail());
            user updatedUser = userRepo.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
