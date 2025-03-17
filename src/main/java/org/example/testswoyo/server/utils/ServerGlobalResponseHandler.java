package org.example.testswoyo.server.utils;


import lombok.extern.slf4j.Slf4j;
import org.example.testswoyo.server.dto.CommonResponse;
import org.example.testswoyo.server.dto.response.AllTopicResponse;
import org.example.testswoyo.server.dto.response.TopicResponse;
import org.example.testswoyo.server.dto.response.VoteInTopicResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ServerGlobalResponseHandler {

    public String success(String message) {
        CommonResponse commonResponse = new CommonResponse(ServerResponse.SUCCESS.toString(), message);
        Optional<String> json = ServerJsonConvector.serialize(commonResponse);
        log.info("valid: {}", json.isPresent());
        return json.orElseGet(() -> sendBadRequest("Unexpected error during JSON serialization"));
    }

    public String sendBadRequest(String message) {
        CommonResponse commonResponse = new CommonResponse(ServerResponse.BAD_REQUEST.toString(), message);
        Optional<String> json = ServerJsonConvector.serialize(commonResponse);
        log.info("valid: {}", json.isPresent());
        return json.orElseGet(() -> sendBadRequest("Unexpected error during JSON serialization"));
    }

    public String sendAllTopic(AllTopicResponse allTopicResponse) {
        CommonResponse commonResponse =
                new CommonResponse(ServerResponse.VIEW_ALL_TOPIC_RESPONSE.toString(), allTopicResponse);
        Optional<String> json = ServerJsonConvector.serialize(commonResponse);
        log.info("valid: {}", json.isPresent());
        return json.orElseGet(() -> sendBadRequest("Unexpected error during JSON serialization"));
    }

    public String sendTopic(TopicResponse votes) {
        CommonResponse commonResponse = new CommonResponse(ServerResponse.VIEW_TOPIC_RESPONSE.toString(), votes);
        Optional<String> json = ServerJsonConvector.serialize(commonResponse);
        log.info("valid: {}", json.isPresent());
        return json.orElseGet(() -> sendBadRequest("Unexpected error during JSON serialization"));
    }

    public String sendVoteInTopic(VoteInTopicResponse voteInTopicResponse) {
        CommonResponse commonResponse =
                new CommonResponse(ServerResponse.VIEW_VOTE_RESPONSE.toString(), voteInTopicResponse);
        Optional<String> json = ServerJsonConvector.serialize(commonResponse);
        log.info("valid: {}", json.isPresent());
        return json.orElseGet(() -> sendBadRequest("Unexpected error during JSON serialization"));
    }
}