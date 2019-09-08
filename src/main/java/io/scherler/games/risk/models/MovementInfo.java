package io.scherler.games.risk.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovementInfo {

    private TerritoryInfo source;

    private TerritoryInfo target;

}
