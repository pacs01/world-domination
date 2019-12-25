package io.scherler.games.worlddomination.models.request.game;

import io.scherler.games.worlddomination.models.request.map.Territory;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Deployment extends Territory {

    public Deployment(String target, int numberOfUnits) {
        super(target);
        this.numberOfUnits = numberOfUnits;
    }

    @NotNull(message = "Number of units is mandatory")
    private int numberOfUnits;
}
