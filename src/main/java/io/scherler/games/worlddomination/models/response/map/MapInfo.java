package io.scherler.games.worlddomination.models.response.map;

import io.scherler.games.worlddomination.entities.map.MapEntity;
import io.scherler.games.worlddomination.models.response.IdentifiableResource;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class MapInfo extends IdentifiableResource {

    public MapInfo(String name,
        List<ContinentInfo> continents) {
        this.name = name;
        this.continents = continents;
    }

    private String name;

    private List<ContinentInfo> continents;

    public static MapInfo from(MapEntity mapEntity) {
        if (mapEntity == null) {
            return null;
        }
        return MapInfo.builder()
            .id(mapEntity.getId())
            .name(mapEntity.getName())
            .continents(mapEntity.getContinents().stream().map(ContinentInfo::from)
                .collect(Collectors.toList()))
            .build();
    }
}
