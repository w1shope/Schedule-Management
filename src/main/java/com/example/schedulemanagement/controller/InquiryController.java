package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.domain.Scheduler;
import com.example.schedulemanagement.dto.InquiryRequestDto;
import com.example.schedulemanagement.service.InquiryService;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class InquiryController {

    private final InquiryService inquiryService;
    private final Scheduler scheduler;

    @PostMapping("/inquiry")
    public String postInquiry(@RequestBody InquiryRequestDto inquiryRequestDto) throws SlackApiException, IOException {
        log.info("dto={}", inquiryRequestDto);
        scheduler.changeCron(inquiryRequestDto);
//        inquiryService.postInquiry(inquiryRequestDto);
        return "ok";
    }
}
