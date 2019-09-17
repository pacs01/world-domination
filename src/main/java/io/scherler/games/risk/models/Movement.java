package io.scherler.games.risk.models;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Movement extends Deployment {

    public Movement(String target, int numberOfUnits, String source) {
        super(target, numberOfUnits);
        this.source = source;
    }

    @NotBlank(message = "Source territory is mandatory")
    private String source;
}
