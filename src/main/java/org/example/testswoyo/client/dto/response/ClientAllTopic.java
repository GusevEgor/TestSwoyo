package org.example.testswoyo.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientAllTopic {
    private List<ClientSmallTopic> topicList;
}
