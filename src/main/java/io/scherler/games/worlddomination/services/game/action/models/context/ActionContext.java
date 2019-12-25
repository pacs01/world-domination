package io.scherler.games.worlddomination.services.game.action.models.context;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.game.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActionContext {

    final GameEntity game;
    final PlayerEntity player;
}
