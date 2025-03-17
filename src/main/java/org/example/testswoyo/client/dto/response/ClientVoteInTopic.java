package org.example.testswoyo.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientVoteInTopic {
    private String voteDescription;
    private List<ClientAnswer> smallVoteResponses;
}
