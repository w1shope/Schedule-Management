package com.example.schedulemanagement.service;

import com.example.schedulemanagement.domain.SlackMessageRequest;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.composition.TextObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@Service
@RequiredArgsConstructor
public class InquiryService {

    // yml정보 가져오기
    @Value(value = "${slack.token}")
    private String token;
    @Value(value = "${slack.channel}")
    private String channel;


    public void postInquiry(SlackMessageRequest requestInfo) throws SlackApiException, IOException {

        // Slack 메세지 보내기
        try {
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText("*요청자 :*\n" + requestInfo.getUser_name()));
            textObjects.add(markdownText("*요청 내용:*\n" + requestInfo.getText()));

            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .blocks(asBlocks(
                            header(header -> header.text(plainText(requestInfo.getUser_name() + " 님 작업을 처리할 시간입니다!"))),
                            divider(),
                            section(section -> section.fields(textObjects)
                            ))).
                    build();
            methods.chatPostMessage(request);
        } catch (SlackApiException | IOException e) {
            throw e;
        }
    }
}