package org.example.senderapp.services;


import lombok.RequiredArgsConstructor;
import org.example.senderapp.entities.User;
import org.example.senderapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    public boolean register(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }


    public User login(String email, String rawPassword) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return user;
            }
        }
        return null;
    }


    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }
}
