package com.example.schedulemanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SlackMessageRequest {
    private String user_id;
    private String user_name;
    private String text;
    private String command;
    private String channel_id;
}
