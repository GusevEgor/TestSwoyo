package org.example.testswoyo.client.utils;


import lombok.extern.slf4j.Slf4j;
import org.example.testswoyo.client.dto.response.ClientAllTopic;
import org.example.testswoyo.client.dto.response.ClientSmallTopic;
import org.example.testswoyo.client.dto.response.ClientTopic;
import org.example.testswoyo.client.dto.response.ClientVoteInTopic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ClientResponseRouter {


    public String success(String json) {
        log.info("Success: {}", json);
        return "Success";
    }

    public String badRequest(String json) {
        log.debug("Bad request: {}", json);
        return "Bad request" + json;
    }

    public String viewAllTopic(Optional<ClientAllTopic> request) {
        if (request.isPresent()) {
            List<ClientSmallTopic> topics = request.get().getTopicList();
            String result = topics
                    .stream()
                    .map(
                            x -> x.getTopicTitle() + " votes in topic: " + x.getVoteCount() + "\n")
                    .reduce((x, y) -> x + y).orElseGet(() -> "No topics yet\n");
            return "All topics:\n" + result;
        } else {
            log.error("Error while parsing json: {}", request);
            return "Error while parsing json: " + request;
        }
    }


    public String viewTopic(Optional<ClientTopic> request) {
        if (request.isPresent()) {
            String result = "Title: " + request.get().getTopicTitle() + "\n" + "Votes:\n";
            String allVotes = request.get().getVotes()
                    .stream()
                    .map(x -> x + "\n")
                    .reduce((x, y) -> x + y)
                    .orElseGet(() -> "No votes yet\n");

            return result + allVotes;
        } else {
            log.error("Error while parsing json: {}", request);
            return "Error while parsing json: " + request;
        }
    }

    public String viewVote(Optional<ClientVoteInTopic> request) {
        if (request.isPresent()) {
            return "Vote description: " + request.get().getVoteDescription() + "\n" + "Answers:\n" +
                    request.get().getSmallVoteResponses()
                            .stream()
                            .map(x -> "Answer: " + x.getAnswer() + " Voice count: " + x.getVoiceCount() + "\n")
                            .reduce((x, y) -> x + y)
                            .orElseGet(() -> "No answers yet\n");
        } else {
            log.error("Error while parsing json: {}", request);
            return "Error while parsing json: " + request;
        }
    }

}
