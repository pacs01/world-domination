package io.scherler.games.risk.models.response.map;

import io.scherler.games.risk.entities.map.ContinentEntity;
import io.scherler.games.risk.models.response.IdentifiableResource;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ContinentInfo extends IdentifiableResource {

    public ContinentInfo(String name,
        List<TerritoryInfo> territories) {
        this.name = name;
        this.territories = territories;
    }

    private String name;

    private List<TerritoryInfo> territories;

    public static ContinentInfo from(ContinentEntity continentEntity) {
        if (continentEntity == null) {
            return null;
        }
        return ContinentInfo.builder()
            .id(continentEntity.getId())
            .name(continentEntity.getName())
            .territories(continentEntity.getTerritories().stream().map(TerritoryInfo::from).collect(
                Collectors.toList()))
            .build();
    }
}
