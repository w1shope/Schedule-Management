package com.example.schedulemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class ScheduleManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleManagementApplication.class, args);
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(4);
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return taskScheduler;
    }
}
