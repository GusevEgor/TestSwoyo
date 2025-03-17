package org.example.testswoyo.server.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateVoteRequestS {
    private String username;
    private String topicTitle;
    private String voteTitle;
    private String voteDescription;
    private Integer numberOfAnswers;
    private List<String> answers;
}
