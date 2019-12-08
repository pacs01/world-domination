package io.scherler.games.risk.services.game.action.models.context;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActionContext {

    final GameEntity game;
    final PlayerEntity player;
}
