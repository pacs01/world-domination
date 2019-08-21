package io.scherler.games.risk.models;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Movement extends Deployment {

    @NotBlank(message = "Source territory is mandatory")
    private String source;
}
