package com.example.schedulemanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableScheduling
public class SlackBotService {

    @Value("${slack.token}")
    private String botToken;
    @Value("${slack.channelId}")
    private String channelId;

    // Send message to Slack
    public void publishMessage(String text) {
        String url = "https://slack.com/api/chat.postMessage?channel=" + channelId + "&text=" + text + "&pretty=1";
        httpResponse(url, botToken, HttpMethod.POST);
    }

    private ResponseEntity<String> httpResponse(String url, String token, HttpMethod method) {
        String requestUrl = url;
        HttpMethod requestMethod = method;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

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

    // get time and set text for slack message
//    public String timeCheckAndSetText() {
//        String prefix = getPrefix();
//
//        LocalTime localTime = LocalTime.now();
//        String hour = String.valueOf(localTime.getHour());
//        String minute = String.valueOf(localTime.getMinute());
//
//        if (hour.equals("21") && minute.equals("0")) {
//            return prefix + "계획 공유";
//        } else if (hour.equals("22") && minute.equals("0")) {
//            return prefix + "공부한 내용 정리";
//        } else {
//            return null;
//        }
//    }


    // get prefix for slack message
//    private String getPrefix() {
//        LocalDate today = LocalDate.now();
//        System.out.println(today);
//
//        String month = String.valueOf(today.getMonthValue());
//        String day = String.valueOf(today.getDayOfMonth());
//
//        String prefix = month + "/" + day + " ";
//        return prefix;
//    }
}