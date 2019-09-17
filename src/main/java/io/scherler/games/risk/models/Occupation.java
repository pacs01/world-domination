package io.scherler.games.risk.models;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Occupation {

    @NotBlank(message = "Target territory is mandatory")
    private String target;
}
