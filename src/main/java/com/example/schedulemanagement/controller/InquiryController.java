package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.domain.Scheduler;
import com.example.schedulemanagement.domain.SlackMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class InquiryController {

    private final Scheduler scheduler;

    @PostMapping("/inquiry")
    public String postInquiry(SlackMessageRequest requestInfo) {
        log.info("dto={}", requestInfo);
        scheduler.changeCron(requestInfo);
        return requestInfo.getUser_name() + "님의 요청을 확인하였습니다.";
    }
}
