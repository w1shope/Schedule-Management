package com.example.schedulemanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Service
@EnableScheduling
public class SlackBotService {

    @Value("${slack.token}")
    private String botToken;
    @Value("${slack.channelId}")
    private String channelId;

    // Send message to Slack
    public void publishMessage(String text) throws UnsupportedEncodingException {
        String url = "https://slack.com/api/chat.postMessage?";
        url += "channel=" + channelId + "&";
        url += "text=" + text + "&";
        url += "pretty=1";
        httpResponse(url, botToken, HttpMethod.POST);
    }

    private ResponseEntity<String> httpResponse(String url, String token, HttpMethod method) {
        String requestUrl = url;
        HttpMethod requestMethod = method;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                requestUrl,
                requestMethod,
                requestEntity,
                String.class
        );

        return responseEntity;
    }
}