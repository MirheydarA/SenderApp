package org.example.senderapp.controllers;

import org.example.senderapp.entities.Account;
import org.example.senderapp.entities.User;
import org.example.senderapp.enums.AccountType;
import org.example.senderapp.enums.Currency;
import org.example.senderapp.repositories.AccountRepository;
import org.example.senderapp.repositories.UserRepository;
import org.example.senderapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @GetMapping("/all")
    @ResponseBody
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/view/{id}")
    @ResponseBody
    public Optional<Account> getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/user/{userId}")
    @ResponseBody
    public Optional<Account> getAccountByUser(@PathVariable int userId) {
        return accountService.getAccountByUserId(userId);
    }

    @GetMapping("/create")
    public String showAccountCreateForm(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("currencies", Currency.values());
        model.addAttribute("accountType", AccountType.values());
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

    @PostMapping("/increaseBalance/{accountId}")
    public String increaseBalance(@PathVariable Long accountId,
                                  @RequestParam BigDecimal amount) {
        accountService.increaseBalance(accountId, amount);
        return "redirect:/dashboard";
    }

    @PostMapping("/decreaseBalance/{accountId}")
    public String decreaseBalance(@PathVariable Long accountId,
                                  @RequestParam BigDecimal amount,
                                  Model model) {
        boolean success = accountService.decreaseBalance(accountId, amount);

        if (!success) {
            Account account = accountService.findById(accountId);
            model.addAttribute("account", account);
            model.addAttribute("errorMessage", "Insufficient balance or inactive account.");
            return "account-details";
        }

        return "redirect:/dashboard";
    }




    @PostMapping("/blockAccount/{userId}")
    public String blockUserAccount(@PathVariable int userId) {
        accountService.blockAccountByUserId(userId);
        return "redirect:/admin/dashboard";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}

