package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import lombok.Value;

@Value
class ActionContext<RequestModel> {

    RequestModel request;
    GameEntity game;
    PlayerEntity player;
}
