package org.example.senderapp.controllers;

import org.example.senderapp.entities.Account;
import org.example.senderapp.entities.User;
import org.example.senderapp.enums.Currency;
import org.example.senderapp.repositories.AccountRepository;
import org.example.senderapp.repositories.UserRepository;
import org.example.senderapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Optional<Account> getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/user/{userId}")
    public Optional<Account> getAccountByUser(@PathVariable int userId) {
        return accountService.getAccountByUserId(userId);
    }

    @GetMapping("/createPage")
    public String showAccountCreateForm(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("currencies", Currency.values());
        return "account-create";
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute Account account, Principal principal) {
        String email = principal.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "redirect:/login?error";
        }

        User user = optionalUser.get();

        account.setUser(user);
        accountRepository.save(account);

        return "redirect:/dashboard";
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
