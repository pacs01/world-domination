package io.scherler.games.risk.models;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Occupation {

    @NotBlank(message = "Target territory is mandatory")
    private String target;
}
