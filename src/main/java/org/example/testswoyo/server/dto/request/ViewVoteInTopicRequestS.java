package org.example.testswoyo.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewVoteInTopicRequestS {
    private String username;
    private String topicTitle;
    private String voteTitle;
}
