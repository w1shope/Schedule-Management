package com.example.schedulemanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InquiryRequestDto {
    private String name;
    private LocalDateTime requestTime;
    private String content;
}
