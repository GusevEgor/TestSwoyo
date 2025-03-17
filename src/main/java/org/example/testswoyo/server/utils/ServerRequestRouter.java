package org.example.testswoyo.server.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.testswoyo.server.dto.request.*;
import org.example.testswoyo.server.service.AnswerService;
import org.example.testswoyo.server.service.TopicService;
import org.example.testswoyo.server.service.VoteService;
import org.springframework.stereotype.Component;
import org.example.testswoyo.server.service.UserService;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServerRequestRouter {
    private final ServerGlobalResponseHandler serverGlobalResponseHandler;
    private final UserService userService;
    private final TopicService topicService;
    private final VoteService voteService;
    private final AnswerService answerService;


    public String login(Optional<LoginUserRequest> loginRequest) {
        if (loginRequest.isPresent()) {
            return userService.login(loginRequest.get());
        } else {
            return serverGlobalResponseHandler.sendBadRequest("Bad request");
        }
    }

    public String createTopic(Optional<CreateTopicRequest> createTopicRequest) {
        if (createTopicRequest.isPresent()) {
            return topicService.createTopic(createTopicRequest.get());
        } else {
            return serverGlobalResponseHandler.sendBadRequest("Bad request");
        }
    }

    public String viewAllTopicRequest() {
        return topicService.viewAllTopic();
    }

    public String viewTopic(Optional<ViewTopicRequest> viewTopicRequest) {
        if (viewTopicRequest.isPresent()) {
            return topicService.viewTopic(viewTopicRequest.get());
        } else {
            return serverGlobalResponseHandler.sendBadRequest("Bad request");
        }
    }

    public String viewVoteInTopic(Optional<ViewVoteInTopicRequestS> viewVoteInTopicRequest) {
        if (viewVoteInTopicRequest.isPresent()) {
            return voteService.viewVote(viewVoteInTopicRequest.get());
        } else {
            return serverGlobalResponseHandler.sendBadRequest("Bad request");
        }
    }

    public String createVote(Optional<CreateVoteRequestS> createVoteRequest) {
        if (createVoteRequest.isPresent()) {
            return voteService.createVote(createVoteRequest.get());
        } else {
            return serverGlobalResponseHandler.sendBadRequest("Bad request");
        }
    }

    public String answerInVoteTopic(Optional<AnswerRequestS> answerRequestS) {
        if (answerRequestS.isPresent()) {
            return answerService.setAnswer(answerRequestS.get());
        } else {
            return serverGlobalResponseHandler.sendBadRequest("Bad request");
        }
    }

    public String deleteVote(Optional<DeleteVoteRequestS> deleteVoteRequest) {
        if (deleteVoteRequest.isPresent()) {
            return voteService.deleteVote(deleteVoteRequest.get());
        } else {
            return serverGlobalResponseHandler.sendBadRequest("Bad request");
        }
    }

}

