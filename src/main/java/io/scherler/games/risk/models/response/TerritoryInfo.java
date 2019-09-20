package io.scherler.games.risk.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TerritoryInfo {

    private String territory;

    private int numberOfUnits;
}
