package org.example.testswoyo.server.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.testswoyo.server.dto.request.CreateVoteRequestS;
import org.example.testswoyo.server.entity.Topic;
import org.example.testswoyo.server.entity.User;
import org.example.testswoyo.server.entity.Vote;
import org.example.testswoyo.server.mapper.VoteMapper;
import org.example.testswoyo.server.repository.AnswerRepository;
import org.example.testswoyo.server.repository.VoteRepository;
import org.example.testswoyo.server.utils.ServerGlobalResponseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class VoteServiceTest {

    @Mock
    private ServerGlobalResponseHandler serverGlobalResponseHandler;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoteMapper voteMapper;

    @Mock
    private TopicService topicService;

    @Mock
    private UserService userService;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private VoteService voteService;

    private CreateVoteRequestS createVoteRequest;
    private Vote vote;
    private User user;
    private Topic topic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createVoteRequest = new CreateVoteRequestS();
        createVoteRequest.setTopicTitle("testTopic");
        createVoteRequest.setVoteTitle("testVote");
        createVoteRequest.setVoteDescription("testDescription");
        createVoteRequest.setAnswers(Arrays.asList("answer1", "answer2"));
        createVoteRequest.setUsername("testUser");

        vote = new Vote();
        vote.setTitle("testVote");

        user = new User();
        user.setName("testUser");

        topic = new Topic();
        topic.setTitle("testTopic");
    }

    @Test
    void testCreateVote_TopicDoesNotExist() {
        when(topicService.checkTopicExists(createVoteRequest.getTopicTitle())).thenReturn(false);

        String result = voteService.createVote(createVoteRequest);

        verify(serverGlobalResponseHandler).sendBadRequest(
                "Topic title" + createVoteRequest.getTopicTitle() + "does not exist");
    }

    @Test
    void testCreateVote_Success() {
        when(topicService.checkTopicExists(createVoteRequest.getTopicTitle())).thenReturn(true);
        when(topicService.getTopicByTitle(createVoteRequest.getTopicTitle())).thenReturn(topic);
        when(userService.getUserByName(createVoteRequest.getUsername())).thenReturn(user);
        when(voteMapper.voteFromRequest(createVoteRequest)).thenReturn(vote);

        when(voteRepository.save(vote)).thenReturn(vote);
        when(answerRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        when(serverGlobalResponseHandler.success("Vote created successfully")).thenReturn("Vote created successfully");

        String result = voteService.createVote(createVoteRequest);

        assertEquals("Vote created successfully", result);

        verify(voteRepository).save(vote);
        verify(answerRepository).saveAll(anyList());
        verify(serverGlobalResponseHandler).success("Vote created successfully");
    }
}
