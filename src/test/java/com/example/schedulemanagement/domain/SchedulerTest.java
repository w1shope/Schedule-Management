package com.example.schedulemanagement.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SchedulerTest {

    @Autowired
    Scheduler scheduler;

    @Test
    @GetMapping("/api/test")
    void getCronExpression() {
        String input = "10월 31일 21시 30분에 알림";
        String cron = scheduler.getCronExpression(input);
        System.out.println(cron);
    }
}