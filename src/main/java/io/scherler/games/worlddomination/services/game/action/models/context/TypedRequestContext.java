package io.scherler.games.worlddomination.services.game.action.models.context;

import lombok.Getter;

@Getter
public class TypedRequestContext<T> extends RequestContext {

    public TypedRequestContext(long gameId, long playerId, T request) {
        super(gameId, playerId);
        this.request = request;
    }

    final T request;
}
