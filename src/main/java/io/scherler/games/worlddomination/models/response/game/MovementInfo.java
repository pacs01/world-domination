package io.scherler.games.worlddomination.models.response.game;

import io.scherler.games.worlddomination.models.response.map.TerritoryInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovementInfo {

    private TerritoryInfo source;

    private TerritoryInfo target;

}
