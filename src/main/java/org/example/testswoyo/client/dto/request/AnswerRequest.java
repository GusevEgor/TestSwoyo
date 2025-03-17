package org.example.testswoyo.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AnswerRequest {
    private String username;
    private String topicTitle;
    private String voteTitle;
    private String answer;
}
