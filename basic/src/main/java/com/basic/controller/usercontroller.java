
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

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class usercontroller {

   
    @Autowired
    private userRepo userRepo;

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<user>> getUsers() {
    log.info("Fetching all users");
        List<user> users = userRepo.findAll();
    log.debug("Found {} users", users.size());
        return ResponseEntity.ok(users);
    }

    // Get user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<user> getUserById(@PathVariable Long id) {
    log.info("Fetching user with id: {}", id);
        Optional<user> userOpt = userRepo.findById(id);
        if (userOpt.isPresent()) {
        log.debug("User found: {}", userOpt.get());
            return ResponseEntity.ok(userOpt.get());
        } else {
        log.warn("User not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Create new user
    @PostMapping("/users")
    public ResponseEntity<user> createUser(@RequestBody user userObj) {
    log.info("Creating new user: {}", userObj);
        user savedUser = userRepo.save(userObj);
    log.debug("User created with id: {}", savedUser.getId());
        return ResponseEntity.ok(savedUser);
    }

    // Update user
    @PutMapping("/users/{id}")
    public ResponseEntity<user> updateUser(@PathVariable Long id, @RequestBody user userObj) {
    log.info("Updating user with id: {}", id);
        Optional<user> userOpt = userRepo.findById(id);
        if (userOpt.isPresent()) {
            user existingUser = userOpt.get();
        log.debug("Current user data: {}", existingUser);
            existingUser.setName(userObj.getName());
            existingUser.setAge(userObj.getAge());
            existingUser.setEmail(userObj.getEmail());
            user updatedUser = userRepo.save(existingUser);
        log.debug("Updated user data: {}", updatedUser);
            return ResponseEntity.ok(updatedUser);
        } else {
        log.warn("User not found for update with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    log.info("Deleting user with id: {}", id);
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        log.debug("User deleted with id: {}", id);
            return ResponseEntity.noContent().build();
        } else {
        log.warn("User not found for deletion with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
        // Create multiple users
    @PostMapping("/users/batch")
    public ResponseEntity<List<user>> createUsers(@RequestBody List<user> users) {
        log.info("Creating multiple users: {}", users.size());
        List<user> savedUsers = userRepo.saveAll(users);
        log.debug("Created {} users", savedUsers.size());
        return ResponseEntity.ok(savedUsers);
    }
}
