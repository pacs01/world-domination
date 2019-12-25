package io.scherler.games.worlddomination.services.game.action.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.util.Assert;

@Data
public class Party {

    private final int totalUnits;

    @Setter(AccessLevel.NONE)
    private int lostUnits = 0;

    public int killUnit() {
        Assert.isTrue(getRemainingUnits() > 0, "fatal error: more units lost than available");
        return ++lostUnits;
    }

    public int getRemainingUnits() {
        return totalUnits - lostUnits;
    }
}
