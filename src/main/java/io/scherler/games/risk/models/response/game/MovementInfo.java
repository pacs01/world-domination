package io.scherler.games.risk.models.response.game;

import io.scherler.games.risk.models.response.map.TerritoryInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovementInfo {

    private TerritoryInfo source;

    private TerritoryInfo target;

}
