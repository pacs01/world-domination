package io.scherler.games.risk.models.response;

import io.scherler.games.risk.entities.game.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TurnResult {

    private PlayerEntity nextPlayer;

    private Card card;
}
