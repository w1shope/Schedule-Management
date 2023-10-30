package com.example.schedulemanagement.domain;

import com.example.schedulemanagement.dto.InquiryRequestDto;
import com.example.schedulemanagement.service.InquiryService;
import com.example.schedulemanagement.service.SlackBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class Scheduler {
    private final InquiryService inquiryService;
    private final SlackBotService slackBotService;
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> future;
    private String cron = "* * * * * *";

    public void changeCron(@RequestBody InquiryRequestDto inquiryRequestDto) {
        if (future != null) future.cancel(true);
        this.future = null;
        this.cron = getCronExpression(inquiryRequestDto.getContent()) + " ?";
        this.startSchedule(inquiryRequestDto);
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
    public void startSchedule(String content) {
        ScheduledFuture<?> future = this.taskScheduler.schedule(() -> {
                    slackBotService.publishMessage(content);
                },
                new CronTrigger(cron));
        this.future = future;
    }

    public void startSchedule(InquiryRequestDto inquiryRequestDto) {
        ScheduledFuture<?> future = this.taskScheduler.schedule(() -> {
                    try {
                        inquiryService.postInquiry(inquiryRequestDto);
                    } catch (Exception ex) {
                        return;
                    }
                },
                new CronTrigger(cron));
        this.future = future;
    }
}