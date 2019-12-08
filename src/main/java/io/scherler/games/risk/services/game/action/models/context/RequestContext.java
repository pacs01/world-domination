package io.scherler.games.risk.services.game.action.models.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestContext {

    final long gameId;
    final long playerId;
}
