package io.scherler.games.worlddomination.test.unit.testdata;

import static io.scherler.games.worlddomination.test.unit.testdata.Game.Games.GAME_ONE;
import static io.scherler.games.worlddomination.test.unit.testdata.Game.Players.PLAYER_FOUR;
import static io.scherler.games.worlddomination.test.unit.testdata.Game.Players.PLAYER_ONE;
import static io.scherler.games.worlddomination.test.unit.testdata.Game.Players.PLAYER_THREE;
import static io.scherler.games.worlddomination.test.unit.testdata.Game.Players.PLAYER_TWO;
import static io.scherler.games.worlddomination.test.unit.testdata.Game.Users.USER_FOUR;
import static io.scherler.games.worlddomination.test.unit.testdata.Game.Users.USER_ONE;
import static io.scherler.games.worlddomination.test.unit.testdata.Game.Users.USER_THREE;
import static io.scherler.games.worlddomination.test.unit.testdata.Game.Users.USER_TWO;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Maps.HELLO_WORLD;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.AFGHANISTAN;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.ALASKA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.ALBERTA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.ARGENTINA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.BRAZIL;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.CENTRAL_AMERICA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.CHINA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.CONGO;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.EASTERN_AUSTRALIA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.EASTERN_UNITED_STATES;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.EAST_AFRICA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.EGYPT;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.GREAT_BRITAIN;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.GREENLAND;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.ICELAND;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.INDIA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.INDONESIA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.IRKUTSK;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.JAPAN;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.KAMCHATKA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.MADAGASCAR;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.MIDDLE_EAST;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.MONGOLIA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.NEW_GUINEA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.NORTHERN_EUROPE;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.NORTHWEST_TERRITORY;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.NORTH_AFRICA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.ONTARIO;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.PERU;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.QUEBEC;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.SCANDINAVIA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.SIAM;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.SIBERIA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.SOUTHERN_EUROPE;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.SOUTH_AFRICA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.UKRAINE;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.URAL;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.VENEZUELA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.WESTERN_AUSTRALIA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.WESTERN_EUROPE;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.WESTERN_UNITED_STATES;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.YAKUTSK;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.game.OccupationEntity;
import io.scherler.games.worlddomination.entities.game.PlayerEntity;
import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import io.scherler.games.worlddomination.entities.map.MapEntity;
import io.scherler.games.worlddomination.entities.map.TerritoryEntity;
import io.scherler.games.worlddomination.models.PlayerColor;
import io.scherler.games.worlddomination.test.unit.testdata.World.Maps;
import io.scherler.games.worlddomination.test.unit.testdata.World.Territories;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.val;

@Getter
public class Game {

    public enum Users {
        USER_ONE("userOne"),
        USER_TWO("userTwo"),
        USER_THREE("userThree"),
        USER_FOUR("userFour");

        private final String name;

        Users(String name) {
            this.name = name;
        }

        UserAccountEntity toUpstreamEntity() {
            val userAccount = new UserAccountEntity(name);
            userAccount.setId(Integer.toUnsignedLong(this.ordinal()));

            return userAccount;
        }

        public UserAccountEntity toEntity() {
            return toEntity(this.toUpstreamEntity());
        }

        public UserAccountEntity toEntity(UserAccountEntity userAccount) {
            val games = Games.getAllForUserAccount(userAccount);
            userAccount.setGames(games);
            val players = Players.getAllForUserAccount(userAccount);
            userAccount.setPlayers(players);
            val maps = Maps.getAllForUserAccount(userAccount);
            userAccount.setMaps(maps);

            return userAccount;
        }

        public static Set<UserAccountEntity> getAll() {
            return Arrays.stream(Users.values())
                .map(Users::toUpstreamEntity)
                .collect(Collectors.toSet());
        }
    }

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

        GameEntity toUpstreamEntity() {
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

    public enum Players {
        PLAYER_ONE(GAME_ONE, USER_ONE, PlayerColor.RED, 10),
        PLAYER_TWO(GAME_ONE, USER_TWO, PlayerColor.GREEN, 10),
        PLAYER_THREE(GAME_ONE, USER_THREE, PlayerColor.BLUE, 10),
        PLAYER_FOUR(GAME_ONE, USER_FOUR, PlayerColor.YELLOW, 10);

        private final Games game;
        private final Users userAccount;
        private final PlayerColor color;
        private final int unitsToDeploy;

        Players(Games game, Users userAccount, PlayerColor color, int unitsToDeploy) {
            this.game = game;
            this.userAccount = userAccount;
            this.color = color;
            this.unitsToDeploy = unitsToDeploy;
        }

        PlayerEntity toUpstreamEntity() {
            val player = new PlayerEntity(game.toUpstreamEntity(), userAccount.toUpstreamEntity(), this.ordinal(),
                color, unitsToDeploy);
            player.setId(Integer.toUnsignedLong(this.ordinal()));

            return player;
        }

        public PlayerEntity toEntity() {
            return toEntity(this.toUpstreamEntity());
        }

        public PlayerEntity toEntity(PlayerEntity player) {
            val occupations = Occupations.getAllForPlayer(player);
            player.setOccupations(occupations);

            return player;
        }

        public static Set<PlayerEntity> getAll() {
            return Arrays.stream(Players.values())
                .map(Players::toUpstreamEntity)
                .collect(Collectors.toSet());
        }

        public static Set<PlayerEntity> getAllForGame(GameEntity game) {
            return Arrays.stream(Players.values())
                .map(Players::toUpstreamEntity)
                .filter(e -> e.getGame().equals(game))
                .peek(e -> e.setGame(game))
                .collect(Collectors.toSet());
        }

        public static Set<PlayerEntity> getAllForUserAccount(UserAccountEntity userAccount) {
            return Arrays.stream(Players.values())
                .map(Players::toUpstreamEntity)
                .filter(e -> e.getUserAccount().equals(userAccount))
                .peek(e -> e.setUserAccount(userAccount))
                .collect(Collectors.toSet());
        }
    }

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

        OccupationEntity toUpstreamEntity() {
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
}