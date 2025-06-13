package org.example.senderapp.services;

import org.example.senderapp.entities.Account;
import org.example.senderapp.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> getAccountByUserId(int userId) {
        return accountRepository.findByUserId(userId);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    public Account findById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }


    public boolean increaseBalance(Long accountId, BigDecimal amount) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            if (!account.isActive()) {
                return false;
            }

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }

            BigDecimal newBalance = account.getBalance().add(amount);
            account.setBalance(newBalance);
            accountRepository.save(account);
            return true;
        }
        return false;
    }


    public boolean decreaseBalance(Long accountId, BigDecimal amount) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            if (!account.isActive()) {
                return false; // Cannot decrease if account is not active
            }

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }

            if (account.getBalance().compareTo(amount) < 0) {
                return false;
            }

            BigDecimal newBalance = account.getBalance().subtract(amount);
            account.setBalance(newBalance);
            accountRepository.save(account);
            return true;
        }
        return false;
    }


    public void applyMonthlyAccountUpdate() {
        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            if (!account.isActive()) continue;

            BigDecimal balance = account.getBalance();

            switch (account.getType()) {
                case "SAVINGS" -> {
                    BigDecimal interest = balance.multiply(BigDecimal.valueOf(0.01));
                    account.setBalance(balance.add(interest));
                }
                case "CHECKING" -> {
                    BigDecimal fee = BigDecimal.valueOf(5);
                    if (balance.compareTo(fee) >= 0) {
                        account.setBalance(balance.subtract(fee));
                    }
                }
            }

            accountRepository.save(account);
        }
    }


    public boolean blockAccountByUserId(int userId) {
        Optional<Account> accountOptional = accountRepository.findByUserId(userId);
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                account.setActive(false);
                accountRepository.save(account);
                return true;
            }
            return false;
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
