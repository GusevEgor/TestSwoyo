package org.example.testswoyo.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SmallTopicResponse {
    private String topicTitle;
    private Integer voteCount;

}
