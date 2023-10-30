package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.dto.InquiryRequestDto;
import com.example.schedulemanagement.service.InquiryService;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class InquiryController {

    private final InquiryService inquiryService;

//    @Value("${slack.token}")
//    private String token;



    @PostMapping("/inquiry")
    public String postInquiry(@RequestBody InquiryRequestDto inquiryRequestDto) throws SlackApiException, IOException {
        log.info("dto={}", inquiryRequestDto);
        inquiryService.postInquiry(inquiryRequestDto);
        return "ok";
    }

//    @GetMapping("/token")
//    public String getToken() {
//        return token;
//    }
}
