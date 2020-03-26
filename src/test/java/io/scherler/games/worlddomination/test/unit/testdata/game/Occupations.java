package io.scherler.games.worlddomination.test.unit.testdata.game;

import static io.scherler.games.worlddomination.test.unit.testdata.game.Games.GAME_ONE;
import static io.scherler.games.worlddomination.test.unit.testdata.game.Players.PLAYER_FOUR;
import static io.scherler.games.worlddomination.test.unit.testdata.game.Players.PLAYER_ONE;
import static io.scherler.games.worlddomination.test.unit.testdata.game.Players.PLAYER_THREE;
import static io.scherler.games.worlddomination.test.unit.testdata.game.Players.PLAYER_TWO;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.AFGHANISTAN;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.ALASKA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.ALBERTA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.ARGENTINA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.BRAZIL;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.CENTRAL_AMERICA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.CHINA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.CONGO;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.EASTERN_AUSTRALIA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.EASTERN_UNITED_STATES;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.EAST_AFRICA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.EGYPT;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.GREAT_BRITAIN;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.GREENLAND;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.ICELAND;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.INDIA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.INDONESIA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.IRKUTSK;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.JAPAN;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.KAMCHATKA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.MADAGASCAR;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.MIDDLE_EAST;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.MONGOLIA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.NEW_GUINEA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.NORTHERN_EUROPE;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.NORTHWEST_TERRITORY;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.NORTH_AFRICA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.ONTARIO;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.PERU;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.QUEBEC;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.SCANDINAVIA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.SIAM;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.SIBERIA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.SOUTHERN_EUROPE;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.SOUTH_AFRICA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.UKRAINE;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.URAL;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.VENEZUELA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.WESTERN_AUSTRALIA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.WESTERN_EUROPE;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.WESTERN_UNITED_STATES;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.YAKUTSK;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.game.OccupationEntity;
import io.scherler.games.worlddomination.entities.game.PlayerEntity;
import io.scherler.games.worlddomination.entities.map.TerritoryEntity;
import io.scherler.games.worlddomination.test.unit.testdata.map.Territories;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.val;

public enum Occupations {
    ALASKA_OCCUPATION(GAME_ONE, ALASKA, PLAYER_ONE, 5, 0),
    NORTHWEST_TERRITORY_OCCUPATION(GAME_ONE, NORTHWEST_TERRITORY, PLAYER_ONE, 1, 0),
    GREENLAND_OCCUPATION(GAME_ONE, GREENLAND, PLAYER_ONE, 2, 0),
    ALBERTA_OCCUPATION(GAME_ONE, ALBERTA, PLAYER_ONE, 1, 0),
    ONTARIO_OCCUPATION(GAME_ONE, ONTARIO, PLAYER_ONE, 3, 0),
    QUEBEC_OCCUPATION(GAME_ONE, QUEBEC, PLAYER_ONE, 4, 0),
    WESTERN_UNITED_STATES_OCCUPATION(GAME_ONE, WESTERN_UNITED_STATES, PLAYER_ONE, 4, 0),
    EASTERN_UNITED_STATES_OCCUPATION(GAME_ONE, EASTERN_UNITED_STATES, PLAYER_ONE, 5, 0),
    CENTRAL_AMERICA_OCCUPATION(GAME_ONE, CENTRAL_AMERICA, PLAYER_TWO, 5, 0),
    VENEZUELA_OCCUPATION(GAME_ONE, VENEZUELA, PLAYER_ONE, 2, 0),
    PERU_OCCUPATION(GAME_ONE, PERU, PLAYER_TWO, 9, 0),
    BRAZIL_OCCUPATION(GAME_ONE, BRAZIL, PLAYER_TWO, 3, 0),
    ARGENTINA_OCCUPATION(GAME_ONE, ARGENTINA, PLAYER_TWO, 4, 0),
    NORTH_AFRICA_OCCUPATION(GAME_ONE, NORTH_AFRICA, PLAYER_THREE, 5, 0),
    EGYPT_OCCUPATION(GAME_ONE, EGYPT, PLAYER_THREE, 1, 0),
    EAST_AFRICA_OCCUPATION(GAME_ONE, EAST_AFRICA, PLAYER_THREE, 7, 0),
    CONGO_OCCUPATION(GAME_ONE, CONGO, PLAYER_THREE, 2, 0),
    SOUTH_AFRICA_OCCUPATION(GAME_ONE, SOUTH_AFRICA, PLAYER_THREE, 2, 0),
    MADAGASCAR_OCCUPATION(GAME_ONE, MADAGASCAR, PLAYER_THREE, 3, 0),
    ICELAND_OCCUPATION(GAME_ONE, ICELAND, PLAYER_ONE, 1, 0),
    SCANDINAVIA_OCCUPATION(GAME_ONE, SCANDINAVIA, PLAYER_THREE, 6, 0),
    UKRAINE_OCCUPATION(GAME_ONE, UKRAINE, PLAYER_FOUR, 4, 0),
    GREAT_BRITAIN_OCCUPATION(GAME_ONE, GREAT_BRITAIN, PLAYER_FOUR, 3, 0),
    NORTHERN_EUROPE_OCCUPATION(GAME_ONE, NORTHERN_EUROPE, PLAYER_FOUR, 2, 0),
    SOUTHERN_EUROPE_OCCUPATION(GAME_ONE, SOUTHERN_EUROPE, PLAYER_FOUR, 1, 0),
    WESTERN_EUROPE_OCCUPATION(GAME_ONE, WESTERN_EUROPE, PLAYER_FOUR, 1, 0),
    INDONESIA_OCCUPATION(GAME_ONE, INDONESIA, PLAYER_ONE, 1, 0),
    NEW_GUINEA_OCCUPATION(GAME_ONE, NEW_GUINEA, PLAYER_ONE, 5, 0),
    WESTERN_AUSTRALIA_OCCUPATION(GAME_ONE, WESTERN_AUSTRALIA, PLAYER_ONE, 3, 0),
    EASTERN_AUSTRALIA_OCCUPATION(GAME_ONE, EASTERN_AUSTRALIA, PLAYER_ONE, 4, 0),
    SIAM_OCCUPATION(GAME_ONE, SIAM, PLAYER_ONE, 6, 0),
    INDIA_OCCUPATION(GAME_ONE, INDIA, PLAYER_ONE, 1, 0),
    CHINA_OCCUPATION(GAME_ONE, CHINA, PLAYER_TWO, 1, 0),
    MONGOLIA_OCCUPATION(GAME_ONE, MONGOLIA, PLAYER_TWO, 3, 0),
    JAPAN_OCCUPATION(GAME_ONE, JAPAN, PLAYER_TWO, 3, 0),
    IRKUTSK_OCCUPATION(GAME_ONE, IRKUTSK, PLAYER_TWO, 2, 0),
    YAKUTSK_OCCUPATION(GAME_ONE, YAKUTSK, PLAYER_TWO, 5, 0),
    KAMCHATKA_OCCUPATION(GAME_ONE, KAMCHATKA, PLAYER_THREE, 1, 0),
    SIBERIA_OCCUPATION(GAME_ONE, SIBERIA, PLAYER_THREE, 1, 0),
    AFGHANISTAN_OCCUPATION(GAME_ONE, AFGHANISTAN, PLAYER_THREE, 7, 0),
    URAL_OCCUPATION(GAME_ONE, URAL, PLAYER_FOUR, 6, 0),
    MIDDLE_EAST_OCCUPATION(GAME_ONE, MIDDLE_EAST, PLAYER_FOUR, 5, 0);

    private final Games game;
    private final Territories territory;
    private final Players player;
    private final int units;
    private final int currentRound;

    Occupations(Games game, Territories territory, Players player, int units,
        int currentRound) {
        this.game = game;
        this.territory = territory;
        this.player = player;
        this.units = units;
        this.currentRound = currentRound;
    }

    public OccupationEntity toEntity() {
        return this.toUpstreamEntity();
    }

    public OccupationEntity toUpstreamEntity() {
        val occupation = new OccupationEntity(game.toUpstreamEntity(), territory.toUpstreamEntity(),
            player.toUpstreamEntity(), units, currentRound);
        occupation.setId(Integer.toUnsignedLong(this.ordinal()));

        return occupation;
    }

    public static Set<OccupationEntity> getAll() {
        return Arrays.stream(Occupations.values())
            .map(Occupations::toUpstreamEntity)
            .collect(Collectors.toSet());
    }

    public static Set<OccupationEntity> getAllForGame(GameEntity game) {
        return Arrays.stream(Occupations.values())
            .map(Occupations::toUpstreamEntity)
            .filter(e -> e.getGame().equals(game))
            .peek(e -> e.setGame(game))
            .collect(Collectors.toSet());
    }

    public static Set<OccupationEntity> getAllForPlayer(PlayerEntity player) {
        return Arrays.stream(Occupations.values())
            .map(Occupations::toUpstreamEntity)
            .filter(e -> e.getPlayer().equals(player))
            .peek(e -> e.setPlayer(player))
            .collect(Collectors.toSet());
    }

    public static Set<OccupationEntity> getAllForTerritory(TerritoryEntity territory) {
        return Arrays.stream(Occupations.values())
            .map(Occupations::toUpstreamEntity)
            .filter(e -> e.getTerritory().equals(territory))
            .peek(e -> e.setTerritory(territory))
            .collect(Collectors.toSet());
    }
}
