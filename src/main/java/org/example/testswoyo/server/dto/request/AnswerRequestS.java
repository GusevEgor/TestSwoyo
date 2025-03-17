package org.example.testswoyo.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequestS {
    private String username;
    private String topicTitle;
    private String voteTitle;
    private String answer;
}