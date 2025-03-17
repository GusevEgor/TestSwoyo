package org.example.testswoyo.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoteInTopicResponse {
    private String voteDescription;
    private List<AnswerResponse> smallVoteResponses;
}
