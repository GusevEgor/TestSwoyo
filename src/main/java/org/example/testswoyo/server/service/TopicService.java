package org.example.testswoyo.server.service;

import lombok.RequiredArgsConstructor;
import org.example.testswoyo.server.dto.request.CreateTopicRequest;
import org.example.testswoyo.server.dto.request.ViewTopicRequest;
import org.example.testswoyo.server.dto.response.*;
import org.example.testswoyo.server.entity.Topic;
import org.example.testswoyo.server.entity.Vote;
import org.example.testswoyo.server.mapper.TopicMapper;
import org.example.testswoyo.server.repository.TopicRepository;
import org.example.testswoyo.server.utils.ServerGlobalResponseHandler;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicMapper topicMapper;
    private final UserService userService;
    private final TopicRepository topicRepository;
    private final ServerGlobalResponseHandler serverGlobalResponseHandler;


    public String createTopic(CreateTopicRequest createTopicRequest) {
        if (topicRepository.existsByTitle(createTopicRequest.getTitle())) {
            String message = String.format(
                    "Topic %s already exists, title must be unique", createTopicRequest.getTitle());
            return serverGlobalResponseHandler.sendBadRequest(message);
        }

        Topic topic = topicMapper.convertFromCreateTopicRequestToEntity(createTopicRequest);
        Boolean exists = userService.checkUserExists(createTopicRequest.getUsername());
        if (exists) {
            topicRepository.save(topic);
            return serverGlobalResponseHandler.success("Create topic success");
        } else {
            return serverGlobalResponseHandler.sendBadRequest("User does not exist");
        }
    }


    public String viewAllTopic() {
        AllTopicResponse result = new AllTopicResponse(topicRepository.findAll()
                .stream()
                .map(
                        topic -> {
                            SmallTopicResponse smallTopicResponse = new SmallTopicResponse();
                            smallTopicResponse.setTopicTitle(topic.getTitle());
                            smallTopicResponse.setVoteCount(topic.getVotes().size());
                            return smallTopicResponse;
                        }
                )
                .toList());

        return serverGlobalResponseHandler.sendAllTopic(result);
    }


    public String viewTopic(ViewTopicRequest viewTopicRequest) {
        if (!topicRepository.existsByTitle(viewTopicRequest.getTopicTitle())) {
            return serverGlobalResponseHandler.sendBadRequest("Topic does not exist");
        }

        Topic topic = topicRepository.findByTitle(viewTopicRequest.getTopicTitle());
        List<String> allVotes = topic.getVotes()
                .stream()
                .map(Vote::getTitle)
                .toList();
        TopicResponse result = new TopicResponse(topic.getTitle(), allVotes);

        return serverGlobalResponseHandler.sendTopic(result);
    }


    public Boolean checkTopicExists(String topicTitle) {
        return topicRepository.existsByTitle(topicTitle);
    }

    public Topic getTopicByTitle(String topicTitle) {
        return topicRepository.findByTitle(topicTitle);
    }

}
