package org.example.testswoyo.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.testswoyo.server.dto.request.CreateVoteRequestS;
import org.example.testswoyo.server.dto.request.DeleteVoteRequestS;
import org.example.testswoyo.server.dto.request.ViewVoteInTopicRequestS;
import org.example.testswoyo.server.dto.response.AnswerResponse;
import org.example.testswoyo.server.dto.response.VoteInTopicResponse;
import org.example.testswoyo.server.entity.Answer;
import org.example.testswoyo.server.entity.Vote;
import org.example.testswoyo.server.mapper.VoteMapper;
import org.example.testswoyo.server.repository.AnswerRepository;
import org.example.testswoyo.server.repository.VoteRepository;
import org.example.testswoyo.server.utils.ServerGlobalResponseHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {
    private final ServerGlobalResponseHandler serverGlobalResponseHandler;
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final TopicService topicService;
    private final UserService userService;
    private final AnswerRepository answerRepository;


    public String createVote(CreateVoteRequestS createVoteRequest) {
        if (!topicService.checkTopicExists(createVoteRequest.getTopicTitle())) {
            return serverGlobalResponseHandler.sendBadRequest(
                    "Topic title " + createVoteRequest.getTopicTitle() + " does not exist");
        }

        Vote vote = voteMapper.voteFromRequest(createVoteRequest);
        List<Answer> answers = createVoteRequest.getAnswers().stream().map(a -> {
            Answer answer = new Answer();
            answer.setVote(vote);

            answer.setTitleOfAnswer(a);
            answer.setNumberOfVotes(0);
            return answer;
        }).toList();


        vote.setTitle(createVoteRequest.getVoteTitle());
        vote.setDescription(createVoteRequest.getVoteDescription());
        vote.setTopic(topicService.getTopicByTitle(createVoteRequest.getTopicTitle()));
        vote.setUser(userService.getUserByName(createVoteRequest.getUsername()));
        vote.setAnswers(answers);

        voteRepository.save(vote);
        answerRepository.saveAll(answers);

        return serverGlobalResponseHandler.success("Vote created successfully");
    }

    public String viewVote(ViewVoteInTopicRequestS viewVoteInTopicRequestS) {

        if (!checkVoteExists(viewVoteInTopicRequestS.getVoteTitle())) {
            return serverGlobalResponseHandler.sendBadRequest(
                    "Vote title " + viewVoteInTopicRequestS.getVoteTitle() + " does not exist");
        }

        Vote vote = getVoteByTitle(viewVoteInTopicRequestS.getVoteTitle());
        List<Answer> answers = vote.getAnswers();

        List<AnswerResponse> listAnswerResponse = answers
                .stream()
                .map(
                        answer -> new AnswerResponse(
                                answer.getTitleOfAnswer(),
                                answer.getNumberOfVotes()
                        )
                )
                .toList();
        VoteInTopicResponse voteInTopicResponses = new VoteInTopicResponse(vote.getDescription(), listAnswerResponse);

        return serverGlobalResponseHandler.sendVoteInTopic(voteInTopicResponses);
    }

    @Transactional
    public String deleteVote(DeleteVoteRequestS deleteVoteRequest) {
        if (!topicService.checkTopicExists(deleteVoteRequest.getTopicTitle())) {
            return serverGlobalResponseHandler.sendBadRequest(
                    "Topic title " + deleteVoteRequest.getTopicTitle() + " does not exist");
        }

        if (!checkVoteExists(deleteVoteRequest.getVoteTitle())) {
            return serverGlobalResponseHandler.sendBadRequest(
                    "Vote title " + deleteVoteRequest.getVoteTitle() + " does not exist");
        }

        Vote vote = getVoteByTitle(deleteVoteRequest.getVoteTitle());
        if (!vote.getUser().getName().equals(deleteVoteRequest.getUsername())) {
            return serverGlobalResponseHandler.sendBadRequest(
                    "User " + deleteVoteRequest.getUsername() + " cannot delete vote " + deleteVoteRequest.getVoteTitle());
        }

        log.info("Deleting vote {} with title: {}", vote, vote.getTitle());

        voteRepository.deleteById(vote.getId());
        return serverGlobalResponseHandler.success("Vote deleted successfully");
    }

    public Boolean checkVoteExists(String title) {
        return voteRepository.existsByTitle(title);
    }

    public Vote getVoteByTitle(String title) {
        return voteRepository.findByTitle(title);
    }
}
