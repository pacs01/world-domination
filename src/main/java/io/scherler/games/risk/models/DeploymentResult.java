package io.scherler.games.risk.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeploymentResult {

    private String territory;

    private Integer totalNumberOfUnits;
}
