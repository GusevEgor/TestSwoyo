package org.example.testswoyo.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class CreateVoteRequest {
    private String username;
    private String topicTitle;
    private String voteTitle;
    private String voteDescription;
    private Integer numberOfAnswers;
    private List<String> answers;
}
