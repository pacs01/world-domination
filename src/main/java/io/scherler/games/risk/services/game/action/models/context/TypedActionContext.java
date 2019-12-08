package io.scherler.games.risk.services.game.action.models.context;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import lombok.Getter;

@Getter
public class TypedActionContext<RequestModel> extends ActionContext {

    public TypedActionContext(GameEntity game, PlayerEntity player, RequestModel request) {
        super(game, player);
        this.request = request;
    }

    final RequestModel request;
}
