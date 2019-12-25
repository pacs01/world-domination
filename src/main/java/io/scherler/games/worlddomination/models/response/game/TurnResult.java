package io.scherler.games.worlddomination.models.response.game;

import io.scherler.games.worlddomination.entities.game.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TurnResult {

    private PlayerEntity nextPlayer;

    private Card card;
}
