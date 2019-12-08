package io.scherler.games.risk.services.game.action.models;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleRequestContext {

    final GameEntity game;
    final PlayerEntity player;
}
