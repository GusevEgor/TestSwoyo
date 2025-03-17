package org.example.testswoyo.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class DeleteVoteRequest {
    private String username;
    private String topicTitle;
    private String voteTitle;
}
