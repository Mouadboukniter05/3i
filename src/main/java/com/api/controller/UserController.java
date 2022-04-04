package com.api.controller;

import com.api.model.User;
import com.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository ur;

    @GetMapping("/users")
    public ResponseEntity<?>getAllUsers(){
        List<User> users = ur.findAll();
        if (users.size()>0){
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no users", HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/users/{id}")
    public ResponseEntity<?>getUserPerId(@PathVariable String id){
        Optional<User> userOptional = ur.findById(id);
        if (userOptional.isPresent()){
            return new ResponseEntity<>(userOptional, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no users", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/users/add")
    public ResponseEntity<?>addUser(@RequestBody User user){
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        user.setAdmin(false);
        user.setReported_answers(new ArrayList<>());
        user.setQuestions(new ArrayList<>());
        user.setAnswers(new ArrayList<>());
        user.setReported_questions(new ArrayList<>());
        ur.save(user);
        Optional<User> userOptional = ur.findById(user.getId());
        if (userOptional.isPresent()) {
            return new ResponseEntity<>("ADD-SC", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ADD-NO", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user) {
        Optional<User> userOptional = ur.findById(id);
        if (userOptional.isPresent()) {
            User userup = userOptional.get();
            userup.setNom(user.getNom());
            userup.setEmail(user.getEmail());
            userup.setPassword(user.getPassword());
            userup.setPrenom(user.getPrenom());
            userup.setUpdatedAt(new Date(System.currentTimeMillis()));
            ur.save(userup);
            return new ResponseEntity<>("UP-SC", HttpStatus.OK);
        } else {

            //"Post not found with id = " + id
            return new ResponseEntity<>("UP-NON", HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/users/update/password/{id}")
    public ResponseEntity<?> updatePasswordUser(@PathVariable String id, @RequestBody User user) {
        Optional<User> userOptional = ur.findById(id);
        if (userOptional.isPresent()) {
            User userup = userOptional.get();
            userup.setPassword(user.getPassword());
            userup.setUpdatedAt(new Date(System.currentTimeMillis()));
            ur.save(userup);
            return new ResponseEntity<>("UP-SC", HttpStatus.OK);
        } else {

            //"Post not found with id = " + id
            return new ResponseEntity<>("UP-NON", HttpStatus.NOT_FOUND);
        }
    }

}
