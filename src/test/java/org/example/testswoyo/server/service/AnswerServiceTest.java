package org.example.testswoyo.server.service;


import org.example.testswoyo.server.dto.request.AnswerRequestS;
import org.example.testswoyo.server.entity.Answer;
import org.example.testswoyo.server.entity.Vote;
import org.example.testswoyo.server.repository.AnswerRepository;
import org.example.testswoyo.server.utils.ServerGlobalResponseHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


class AnswerServiceTest {

    @Mock
    private VoteService voteService;

    @Mock
    private ServerGlobalResponseHandler serverGlobalResponseHandler;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerService answerService;

    @Mock
    private AnswerRequestS answerRequest;
    @Mock
    private Vote vote;
    @Mock
    private Answer existingAnswer;
    @Mock
    private Answer nonExistingAnswer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        answerRequest = new AnswerRequestS("test", "test","test", "testAnswer");

        existingAnswer = new Answer(1L, "testAnswer", 1, vote);
        nonExistingAnswer = new Answer();
        vote = new Vote();
        vote.setAnswers(List.of(existingAnswer, nonExistingAnswer));
    }

    @Test
    void testSetAnswerVoteDoesNotExist() {

        when(voteService.checkVoteExists(anyString())).thenReturn(false);

        when(serverGlobalResponseHandler.sendBadRequest(anyString())).thenReturn("Vote title: test does not exist");

        String result = answerService.setAnswer(answerRequest);

        assertEquals("Vote title: test does not exist", result);
        verify(serverGlobalResponseHandler).sendBadRequest("Vote title: test does not exist");
    }

    @Test
    void testSetAnswerAnswerDoesNotExistInVote() {
        when(voteService.checkVoteExists(anyString())).thenReturn(true);
        Vote vote = mock(Vote.class);
        when(voteService.getVoteByTitle(anyString())).thenReturn(vote);

        Answer existingAnswer = new Answer(1L, "existingAnswer", 5, vote);
        when(vote.getAnswers()).thenReturn(List.of(existingAnswer));

        when(serverGlobalResponseHandler.sendBadRequest(anyString())).thenReturn("Answer does not exist");

        AnswerRequestS answerRequest = new AnswerRequestS("test","test", "testVote", "nonExistingAnswer");

        String result = answerService.setAnswer(answerRequest);

        assertEquals("Answer does not exist", result);

        verify(serverGlobalResponseHandler).sendBadRequest("Answer nonExistingAnswer does not exist in vote testVote");
    }

    @Test
    void testSetAnswerAnswerExists() {

        when(voteService.checkVoteExists(anyString())).thenReturn(true);
        when(voteService.getVoteByTitle(anyString())).thenReturn(vote);

        when(serverGlobalResponseHandler.success(anyString())).thenReturn("Answer set successfully");

        answerRequest.setAnswer("testAnswer");

        String result = answerService.setAnswer(answerRequest);

        assertEquals("Answer set successfully", result);
        assertEquals(2, existingAnswer.getNumberOfVotes());
        verify(answerRepository).save(existingAnswer);
        verify(serverGlobalResponseHandler).success("Answer set successfully");
    }

}