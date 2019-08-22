package io.scherler.games.risk.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovementResult {

    private String source;
    private int totalUnitsAtSource;

    private String target;
    private int totalUnitsAtTarget;

}
