package io.scherler.games.risk.models.response;

import io.scherler.games.risk.entities.game.OccupationEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class TerritoryInfo extends IdentifiableResource {

    public TerritoryInfo(String territory, PlayerInfo player, int numberOfUnits) {
        this.territory = territory;
        this.player = player;
        this.numberOfUnits = numberOfUnits;
    }

    private String territory;

    private PlayerInfo player;

    private int numberOfUnits;

    public static TerritoryInfo from(TerritoryEntity territoryEntity) {
        return TerritoryInfo.builder()
            .id(territoryEntity.getId())
            .territory(territoryEntity.getName())
            .build();
    }

    public static TerritoryInfo from(OccupationEntity occupationEntity) {
        return TerritoryInfo.builder()
            .id(occupationEntity.getId())
            .territory(occupationEntity.getTerritory().getName())
            .player(PlayerInfo.from(occupationEntity.getPlayer()))
            .numberOfUnits(occupationEntity.getUnits())
            .build();
    }
}
