package org.example.testswoyo.client.utils;
import org.example.testswoyo.client.dto.response.ClientAllTopic;
import org.example.testswoyo.client.dto.response.ClientTopic;
import org.example.testswoyo.client.dto.response.ClientVoteInTopic;
import org.example.testswoyo.client.utils.responseHandler.ResponseHandlerInterface;


public enum ClientResponses {
    SUCCESS(ClientResponseRouter::success),
    BAD_REQUEST(ClientResponseRouter::badRequest),
    VIEW_ALL_TOPIC_RESPONSE((ClientResponseRouter router, String json)
            -> router.viewAllTopic(ClientJsonConvector.deserialize(json, ClientAllTopic.class))),
    VIEW_VOTE_RESPONSE((ClientResponseRouter router, String json)
            -> router.viewVote(ClientJsonConvector.deserialize(json, ClientVoteInTopic.class))),
    VIEW_TOPIC_RESPONSE((ClientResponseRouter router, String json)
            -> router.viewTopic(ClientJsonConvector.deserialize(json, ClientTopic.class)));

    private final ResponseHandlerInterface handler;

    ClientResponses(ResponseHandlerInterface handler) {this.handler = handler;}

    public String execute(ClientResponseRouter distributor, String json) {
        return handler.handle(distributor, json);
    }
}
