package io.scherler.games.risk.services.game.action.models;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import lombok.Getter;

@Getter
public class TypedRequestContext<RequestModel> extends SimpleRequestContext {

    public TypedRequestContext(GameEntity game,
        PlayerEntity player, RequestModel request) {
        super(game, player);
        this.request = request;
    }

    RequestModel request;
}
