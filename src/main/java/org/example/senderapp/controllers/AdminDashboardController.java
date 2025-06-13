package org.example.senderapp.controllers;

import org.example.senderapp.entities.User;
import org.example.senderapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Principal principal) {
        String email = principal.getName();
        Optional<User> currentUser = userRepository.findByEmail(email);

        currentUser.ifPresent(user -> model.addAttribute("currentUserId", user.getId()));

        List<User> allUsers = userRepository.findAll();
        model.addAttribute("users", allUsers);

        return "admin-dashboard";
    }

}
