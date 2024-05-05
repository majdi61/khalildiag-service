package com.khalildiag.service.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTask {

    @Scheduled(fixedDelay = 25 * 60 * 1000) // 25 minutes in milliseconds
    public void runScheduledTask() {
        // Your task logic goes here
        System.out.println("Running scheduled task every 20 minutes.");
    }
}
