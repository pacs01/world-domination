package io.scherler.games.risk.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovementResult {

    private String source;
    private Integer totalUnitsAtSource;

    private String target;
    private Integer totalUnitsAtTarget;

}
