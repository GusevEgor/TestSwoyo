package org.example.testswoyo.server.utils;

import org.example.testswoyo.server.dto.request.*;


public enum ServerRequest {
    LOGIN((ServerRequestRouter router, String json)
            -> router.login(ServerJsonConvector.deserialize(json, LoginUserRequest.class))),
    CREATE_TOPIC((ServerRequestRouter router, String json)
            -> router.createTopic(ServerJsonConvector.deserialize(json, CreateTopicRequest.class))),
    VIEW_ALL_TOPIC((ServerRequestRouter router, String json)
            -> router.viewAllTopicRequest()),
    VIEW_TOPIC((ServerRequestRouter router, String json)
            -> router.viewTopic(ServerJsonConvector.deserialize(json, ViewTopicRequest.class))),
    VIEW_VOTES_IN_TOPIC((ServerRequestRouter router, String json)
            -> router.viewVoteInTopic(ServerJsonConvector.deserialize(json, ViewVoteInTopicRequestS.class))),
    CREATE_VOTE((ServerRequestRouter router, String json)
            -> router.createVote(ServerJsonConvector.deserialize(json, CreateVoteRequestS.class))),
    ANSWER_IN_VOTE_TOPIC((ServerRequestRouter router, String json)
            -> router.answerInVoteTopic(ServerJsonConvector.deserialize(json, AnswerRequestS.class))),
    DELETE_VOTE((ServerRequestRouter router, String json)
            -> router.deleteVote(ServerJsonConvector.deserialize(json, DeleteVoteRequestS.class)));


    private final RequestHandlerInterface handler;

    ServerRequest(RequestHandlerInterface handler) {
        this.handler = handler;
    }

    public String execute(ServerRequestRouter distributor, String json) {
        return handler.handle(distributor, json);
    }
}
