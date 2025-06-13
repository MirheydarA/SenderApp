package org.example.senderapp.controllers;

import lombok.RequiredArgsConstructor;
import org.example.senderapp.entities.Account;
import org.example.senderapp.entities.User;
import org.example.senderapp.repositories.AccountRepository;
import org.example.senderapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
//@RequiredArgsConstructor
public class DashboardController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        String email = principal.getName();
        System.out.println(email);

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "redirect:/login?error";
        }

        User user = userOptional.get();
        Optional<Account> account = accountRepository.findByUserId(user.getId());

        if (account.isPresent()) {
            model.addAttribute("account", account.get());
            return "account-details"; // Shows details if account exists
        } else {
            return "account-prompt"; // Prompts user to create or link account
        }
    }


}
