package org.example.senderapp.controllers;

import org.example.senderapp.entities.User;
import org.example.senderapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
        userRepo.save(user);
    }
}