package io.scherler.games.risk.exceptions;

import io.scherler.games.risk.entities.game.PlayerEntity;

public class IllegalTurnException extends RuntimeException {

    public IllegalTurnException(String message) {
        super(message);
    }

    public IllegalTurnException(PlayerEntity player) {
        super("Error: this is not player " + player.getColor() + "'s turn.");
    }
}
