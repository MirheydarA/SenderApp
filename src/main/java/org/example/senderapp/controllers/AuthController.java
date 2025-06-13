package org.example.senderapp.controllers;

import lombok.RequiredArgsConstructor;
import org.example.senderapp.entities.User;
import org.example.senderapp.repositories.UserRepository;
import org.example.senderapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user, Model model) {
        boolean registered = authService.register(user);
        if (registered) {
            return "redirect:/login?success";
        } else {
            model.addAttribute("error", "Email already exists.");
            return "register";
        }
    }
}
