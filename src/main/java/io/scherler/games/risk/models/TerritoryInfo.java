package io.scherler.games.risk.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TerritoryInfo {

    private String territory;

    private int numberOfUnits;
}
