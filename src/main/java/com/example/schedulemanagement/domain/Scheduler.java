package com.example.schedulemanagement.domain;

import com.example.schedulemanagement.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class Scheduler {
    private final InquiryService inquiryService;
    private final ThreadPoolTaskScheduler taskScheduler;
    private ScheduledFuture<?> future;
    private String cron = "* * * * * *";

    public void changeCron(SlackMessageRequest requestInfo) {
        if (future != null) future.cancel(true);
        this.future = null;
        this.cron = getCronExpression(requestInfo.getText()) + " ?";
        this.startSchedule(requestInfo);
    }

    public String getCronExpression(String content) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일 HH시 mm분");
        Date date;
        try {
            // parse() : 문자열 -> 객체
            date = dateFormat.parse(content);
            // 파싱한 날짜를 기반으로 cron 표현식 생성
            dateFormat = new SimpleDateFormat("ss mm HH dd MM");
            String format = dateFormat.format(date);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 일정 시작 스케줄러
    public void startSchedule(SlackMessageRequest requestInfo) {
        ScheduledFuture<?> future = this.taskScheduler.schedule(() -> {
                    try {
                        inquiryService.postInquiry(requestInfo);

                    } catch (Exception ex) {
                        return;
                    }
                },
                new CronTrigger(cron));
        this.future = future;
    }
}