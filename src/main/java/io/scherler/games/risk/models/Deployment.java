package io.scherler.games.risk.models;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Deployment extends Occupation {

    public Deployment(String target, int numberOfUnits) {
        super(target);
        this.numberOfUnits = numberOfUnits;
    }

    @NotNull(message = "Number of units is mandatory")
    private int numberOfUnits;
}
