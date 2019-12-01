package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.entities.game.OccupationEntity;
import io.scherler.games.risk.exceptions.IllegalTurnException;

class Validations {

    static void validateActivePlayer(ActionContext<?> context) {
        if (!context.getPlayer().getId().equals(context.getGame().getActivePlayer().getId())) {
            throw new IllegalTurnException(context.getPlayer());
        }
    }

    static void validateNumberOfUnits(int numberOfUnits) {
        if (numberOfUnits < 1) {
            throw new IllegalArgumentException("An action without any units is not possible.");
        }
    }

    static void validateRemainingUnits(OccupationEntity occupation, int numberOfUnits) {
        if (occupation.getUnits() < numberOfUnits + 1) {
            throw new IllegalArgumentException(
                "Not enough units available at territory '" + occupation.getTerritory().getName()
                    + "'. There must remain at least one unit at every conquered place.");
        }
    }
}
