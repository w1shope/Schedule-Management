package com.example.schedulemanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class SlackMessageRequest {
    private String channel;
    private String text;
    private String ts;
}
