package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.InquiryRequestDto;
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


    public void postInquiry(InquiryRequestDto inquiryRequestDto) throws SlackApiException, IOException {

        // Slack 메세지 보내기
        try{
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText("*이름:*\n" + inquiryRequestDto.getName()));
            textObjects.add(markdownText("*문의 제목:*\n" + inquiryRequestDto.getTitle()));
            textObjects.add(markdownText("*문의내용:*\n" + inquiryRequestDto.getContent()));

            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .blocks(asBlocks(
                            header(header -> header.text(plainText( inquiryRequestDto.getName() + "님이 문의를 남겨주셨습니다!"))),
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