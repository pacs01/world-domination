package io.scherler.games.worlddomination.test.unit.testdata.map;

import static io.scherler.games.worlddomination.test.unit.testdata.identity.Users.USER_ONE;

import io.scherler.games.worlddomination.LoadDatabase;
import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import io.scherler.games.worlddomination.entities.map.ContinentEntity;
import io.scherler.games.worlddomination.entities.map.MapEntity;
import io.scherler.games.worlddomination.test.unit.testdata.game.Games;
import io.scherler.games.worlddomination.test.unit.testdata.identity.Users;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.val;

public enum Maps {

    HELLO_WORLD(LoadDatabase.TEST_MAP_NAME, USER_ONE);

    private final String name;
    private final Users creator;

    Maps(String name, Users creator) {
        this.name = name;
        this.creator = creator;
    }

    public MapEntity toUpstreamEntity() {
        val map = new MapEntity(name, creator.toUpstreamEntity());
        map.setId(Integer.toUnsignedLong(this.ordinal()));

        return map;
    }

    public MapEntity toEntity() {
        return toEntity(this.toUpstreamEntity());
    }

    public MapEntity toEntity(MapEntity map) {
        val territories = Territories.getAllForMap(map);
        map.setTerritories(territories);
        val continents = Continents.getAllForMap(map);
        map.setContinents(continents);
        val games = Games.getAllForMap(map);
        map.setGames(games);

        return map;
    }

    public static List<ContinentEntity> getAll() {
        return Arrays.stream(Continents.values())
            .map(Continents::toUpstreamEntity)
            .collect(Collectors.toList());
    }

    public static Set<MapEntity> getAllForUserAccount(UserAccountEntity userAccount) {
        return Arrays.stream(Maps.values())
            .map(Maps::toUpstreamEntity)
            .filter(e -> e.getCreator().equals(userAccount))
            .peek(e -> e.setCreator(userAccount))
            .collect(Collectors.toSet());
    }
}
