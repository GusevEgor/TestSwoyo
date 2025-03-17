package org.example.testswoyo.client.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.testswoyo.client.dto.request.*;
import org.example.testswoyo.client.utils.ClientJsonConvector;
import org.example.testswoyo.client.utils.ClientRequests;
import org.example.testswoyo.client.dto.ClientCommonRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {


    public String sendLoginRequest(String username) {
        LoginRequest loginRequest = new LoginRequest(username);
        ClientCommonRequest commandDto = new ClientCommonRequest(ClientRequests.LOGIN.toString(), loginRequest);
        String request = ClientJsonConvector
                .serialize(commandDto).isPresent() ? ClientJsonConvector.serialize(commandDto).get() : "";
        log.info("Login request sent: {}", request);
        return request;
    }

    public String createTopic(String username, String topicTitle) {
        ClientCreateTopicRequest createTopicRequest = new ClientCreateTopicRequest(username, topicTitle);
        ClientCommonRequest commandDto = new ClientCommonRequest(
                ClientRequests.CREATE_TOPIC.toString(), createTopicRequest);
        String request = ClientJsonConvector
                .serialize(commandDto).isPresent() ? ClientJsonConvector.serialize(commandDto).get() : "";
        log.info("Create topic request sent: {}", request);
        return request;
    }

    public String createVote(String username, String topicTitle, String voteTitle, String voteDescription,
                             Integer numberOfAnswers, List<String> answers) {
        CreateVoteRequest createVoteRequest =
                new CreateVoteRequest(username, topicTitle, voteTitle, voteDescription, numberOfAnswers, answers);
        ClientCommonRequest commandDto = new ClientCommonRequest(
                ClientRequests.CREATE_VOTE.toString(), createVoteRequest);
        String request = ClientJsonConvector
                .serialize(commandDto).isPresent() ? ClientJsonConvector.serialize(commandDto).get() : "";
        log.info("Create vote request sent: {}", request);
        return request;
    }

    public String sendAnswer(String username, String topicTitle, String voteTitle, String answer) {
        AnswerRequest voiceRequest = new AnswerRequest(username, topicTitle, voteTitle, answer);
        ClientCommonRequest commandDto = new ClientCommonRequest(
                ClientRequests.ANSWER_IN_VOTE_TOPIC.toString(), voiceRequest);
        String request = ClientJsonConvector
                .serialize(commandDto).isPresent() ? ClientJsonConvector.serialize(commandDto).get() : "";
        log.info("Answer request sent: {}", request);
        return request;
    }

    public String viewAll(String username) {
        ViewAllRequest viewAllRequest = new ViewAllRequest(username);
        ClientCommonRequest commandDto = new ClientCommonRequest(
                ClientRequests.VIEW_ALL_TOPIC.toString(), viewAllRequest);
        String request = ClientJsonConvector
                .serialize(commandDto).isPresent() ? ClientJsonConvector.serialize(commandDto).get() : "";
        log.info("View all request sent: {}", request);
        return request;
    }


    public String viewTopic(String username, String topicTitle) {
        ClientViewTopicRequest viewTopicRequest = new ClientViewTopicRequest(username, topicTitle);
        ClientCommonRequest commandDto = new ClientCommonRequest(
                ClientRequests.VIEW_TOPIC.toString(), viewTopicRequest);
        String request = ClientJsonConvector
                .serialize(commandDto).isPresent() ? ClientJsonConvector.serialize(commandDto).get() : "";
        log.info("View topic request sent: {}", request);
        return request;
    }

    public String viewVotesInTopic(String username, String topicTitle, String voteTitle) {
        ViewVoteInTopicRequest viewVoteInTopicRequest = new ViewVoteInTopicRequest(username, topicTitle, voteTitle);
        ClientCommonRequest commandDto = new ClientCommonRequest(
                ClientRequests.VIEW_VOTES_IN_TOPIC.toString(), viewVoteInTopicRequest);
        String request = ClientJsonConvector
                .serialize(commandDto).isPresent() ? ClientJsonConvector.serialize(commandDto).get() : "";
        log.info("View votes in topic request sent: {}", request);
        return request;
    }

    public String deleteVote(String username, String topicTitle, String voteTitle) {
        DeleteVoteRequest deleteVoteRequest = new DeleteVoteRequest(username, topicTitle, voteTitle);
        ClientCommonRequest commandDto = new ClientCommonRequest(
                ClientRequests.DELETE_VOTE.toString(), deleteVoteRequest);
        String request = ClientJsonConvector
                .serialize(commandDto).isPresent() ? ClientJsonConvector.serialize(commandDto).get() : "";
        log.info("Delete vote request sent: {}", request);
        return request;
    }
}
