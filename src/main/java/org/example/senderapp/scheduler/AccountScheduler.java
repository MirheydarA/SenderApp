package org.example.senderapp.scheduler;

import org.example.senderapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AccountScheduler {

    @Autowired
    private AccountService accountService;


    @Scheduled(cron = "0 0 0 1 * ?")
    public void runMonthlyAccountUpdate() {
        accountService.applyMonthlyAccountUpdate();
    }
}
