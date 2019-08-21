package io.scherler.games.risk.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Deployment {

    @NotBlank(message = "Target territory is mandatory")
    private String target;

    @NotNull(message = "Number of units is mandatory")
    private Integer numberOfUnits;
}
