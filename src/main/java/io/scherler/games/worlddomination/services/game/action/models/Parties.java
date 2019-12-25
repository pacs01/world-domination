package io.scherler.games.worlddomination.services.game.action.models;

import lombok.Data;

@Data
public class Parties {

    public Parties(int attackers, int defenders) {
        this.attackers = new Party(attackers);
        this.defenders = new Party(defenders);
    }

    private final Party attackers;
    private final Party defenders;
}
