package io.scherler.games.risk.services.game.action.models.context;

import lombok.Getter;

@Getter
public class TypedRequestContext<RequestModel> extends RequestContext {

    public TypedRequestContext(long gameId, long playerId, RequestModel request) {
        super(gameId, playerId);
        this.request = request;
    }

    final RequestModel request;
}
