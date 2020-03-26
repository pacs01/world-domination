package io.scherler.games.worlddomination.test.unit.testdata.map;

import static io.scherler.games.worlddomination.test.unit.testdata.map.Maps.HELLO_WORLD;

import io.scherler.games.worlddomination.entities.map.ContinentEntity;
import io.scherler.games.worlddomination.entities.map.MapEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.val;

public enum Continents {
    NORTH_AMERICA("North America", HELLO_WORLD),
    SOUTH_AMERICA("South America", HELLO_WORLD),
    EUROPE("Europe", HELLO_WORLD),
    ASIA("Asia", HELLO_WORLD),
    AUSTRALIA("Australia", HELLO_WORLD),
    AFRICA("Africa", HELLO_WORLD);

    private final String name;
    private final Maps map;

    Continents(String name, Maps map) {
        this.name = name;
        this.map = map;
    }

    public ContinentEntity toUpstreamEntity() {
        val continent = new ContinentEntity(map.toUpstreamEntity(), name);
        continent.setId(Integer.toUnsignedLong(this.ordinal()));

        return continent;
    }

    public ContinentEntity toEntity() {
        return toEntity(this.toUpstreamEntity());
    }

    public ContinentEntity toEntity(ContinentEntity continent) {
        val territories = Territories.getAllForContinent(continent);
        continent.setTerritories(territories);

        return continent;
    }

    public static List<ContinentEntity> getAll() {
        return Arrays.stream(Continents.values())
            .map(Continents::toUpstreamEntity)
            .collect(Collectors.toList());
    }

    public static Set<ContinentEntity> getAllForMap(MapEntity map) {
        return Arrays.stream(Continents.values())
            .map(Continents::toUpstreamEntity)
            .filter(e -> e.getMap().equals(map))
            .peek(e -> e.setMap(map))
            .collect(Collectors.toSet());
    }
}
