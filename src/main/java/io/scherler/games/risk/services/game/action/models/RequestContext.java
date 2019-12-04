package io.scherler.games.risk.services.game.action.models;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import lombok.Value;

@Value
public class RequestContext<RequestModel> {

    GameEntity game;
    PlayerEntity player;
    RequestModel request;
}
