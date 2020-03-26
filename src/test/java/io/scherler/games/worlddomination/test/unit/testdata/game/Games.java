package io.scherler.games.worlddomination.test.unit.testdata.game;

import static io.scherler.games.worlddomination.test.unit.testdata.identity.Users.USER_ONE;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Maps.HELLO_WORLD;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import io.scherler.games.worlddomination.entities.map.MapEntity;
import io.scherler.games.worlddomination.test.unit.testdata.identity.Users;
import io.scherler.games.worlddomination.test.unit.testdata.map.Maps;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.val;

public enum Games {
    GAME_ONE("newGame", USER_ONE, HELLO_WORLD);

    private final String name;
    private final Users creator;
    private final Maps map;

    Games(String name, Users creator, Maps map) {
        this.name = name;
        this.creator = creator;
        this.map = map;
    }

    public GameEntity toUpstreamEntity() {
        val game = new GameEntity(name, creator.toUpstreamEntity(), map.toUpstreamEntity());
        game.setId(Integer.toUnsignedLong(this.ordinal()));

        return game;
    }

    public GameEntity toEntity() {
        return toEntity(this.toUpstreamEntity());
    }

    public GameEntity toEntity(GameEntity game) {
        val occupations = Occupations.getAllForGame(game);
        game.setOccupations(occupations);
        val players = Players.getAllForGame(game);
        game.setPlayers(players);

        return game;
    }

    public static Set<GameEntity> getAll() {
        return Arrays.stream(Games.values())
            .map(Games::toUpstreamEntity)
            .collect(Collectors.toSet());
    }

    public static Set<GameEntity> getAllForUserAccount(UserAccountEntity userAccount) {
        return Arrays.stream(Games.values())
            .map(Games::toUpstreamEntity)
            .filter(e -> e.getCreator().equals(userAccount))
            .peek(e -> e.setCreator(userAccount))
            .collect(Collectors.toSet());
    }

    public static Set<GameEntity> getAllForMap(MapEntity map) {
        return Arrays.stream(Games.values())
            .map(Games::toUpstreamEntity)
            .filter(e -> e.getMap().equals(map))
            .peek(e -> e.setMap(map))
            .collect(Collectors.toSet());
    }
}
