package io.scherler.games.worlddomination.services.game.action.models.context;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.game.PlayerEntity;
import lombok.Getter;

@Getter
public class TypedActionContext<T> extends ActionContext {

    public TypedActionContext(GameEntity game, PlayerEntity player, T request) {
        super(game, player);
        this.request = request;
    }

    final T request;
}
