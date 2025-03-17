package org.example.testswoyo.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.testswoyo.server.dto.request.AnswerRequestS;
import org.example.testswoyo.server.entity.Answer;
import org.example.testswoyo.server.entity.Vote;
import org.example.testswoyo.server.repository.AnswerRepository;
import org.example.testswoyo.server.utils.ServerGlobalResponseHandler;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {
    private final VoteService voteService;
    private final ServerGlobalResponseHandler serverGlobalResponseHandler;
    private final AnswerRepository answerRepository;

    public String setAnswer(AnswerRequestS answerRequest) {
        if (!voteService.checkVoteExists(answerRequest.getVoteTitle())) {
            return serverGlobalResponseHandler.sendBadRequest(
                    "Vote title: " + answerRequest.getVoteTitle() + " does not exist");
        }


        Vote vote = voteService.getVoteByTitle(answerRequest.getVoteTitle());
        Optional<Answer> answers = vote.getAnswers()
                .stream()
                .filter(
                        answer -> answer.getTitleOfAnswer()
                                .equals(answerRequest.getAnswer()))
                .findFirst();

        if (answers.isPresent()) {
            answers.get().setNumberOfVotes(answers.get().getNumberOfVotes() + 1);

            answerRepository.save(answers.get());
            return serverGlobalResponseHandler.success("Answer set successfully");
        } else {
            return serverGlobalResponseHandler.sendBadRequest(
                    "Answer " + answerRequest.getAnswer() + " does not exist in vote " + answerRequest.getVoteTitle());
        }
    }
}
