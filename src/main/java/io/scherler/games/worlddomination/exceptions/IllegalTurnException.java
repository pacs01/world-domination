package io.scherler.games.worlddomination.exceptions;

import io.scherler.games.worlddomination.entities.game.PlayerEntity;

public class IllegalTurnException extends RuntimeException {

    public IllegalTurnException(String message) {
        super(message);
    }

    public IllegalTurnException(PlayerEntity player) {
        super("Error: this is not player " + player.getColor() + "'s turn.");
    }
}
