package io.scherler.games.worlddomination.test.unit.testdata;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.game.OccupationEntity;
import io.scherler.games.worlddomination.entities.game.PlayerEntity;
import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import io.scherler.games.worlddomination.models.PlayerColor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.val;

@Getter
public class Game {

    private final MockEntityCreator<UserAccountEntity> userGenerator = new MockEntityCreator<>();
    private final MockEntityCreator<GameEntity> gameGenerator = new MockEntityCreator<>();
    private final MockEntityCreator<PlayerEntity> playerGenerator = new MockEntityCreator<>();
    private final MockEntityCreator<OccupationEntity> occupationGenerator =
        new MockEntityCreator<>();

    @Getter(AccessLevel.NONE)
    private final World world;

    private GameEntity game;

    private UserAccountEntity userOne;
    private UserAccountEntity userTwo;
    private UserAccountEntity userThree;
    private UserAccountEntity userFour;
    private List<UserAccountEntity> users;

    private PlayerEntity playerOne;
    private PlayerEntity playerTwo;
    private PlayerEntity playerThree;
    private PlayerEntity playerFour;
    private List<PlayerEntity> players;

    private OccupationEntity alaska;
    private OccupationEntity northwestTerritory;
    private OccupationEntity alberta;
    private OccupationEntity ontario;
    private OccupationEntity greenland;
    private OccupationEntity quebec;
    private OccupationEntity westernUnitedStates;
    private OccupationEntity easternUnitedStates;
    private OccupationEntity centralAmerica;
    private OccupationEntity venezuela;
    private OccupationEntity peru;
    private OccupationEntity brazil;
    private OccupationEntity argentina;
    private OccupationEntity northAfrica;
    private OccupationEntity egypt;
    private OccupationEntity eastAfrica;
    private OccupationEntity congo;
    private OccupationEntity southAfrica;
    private OccupationEntity madagascar;
    private OccupationEntity iceland;
    private OccupationEntity scandinavia;
    private OccupationEntity ukraine;
    private OccupationEntity greatBritain;
    private OccupationEntity northernEurope;
    private OccupationEntity southernEurope;
    private OccupationEntity westernEurope;
    private OccupationEntity indonesia;
    private OccupationEntity newGuinea;
    private OccupationEntity westernAustralia;
    private OccupationEntity easternAustralia;
    private OccupationEntity siam;
    private OccupationEntity india;
    private OccupationEntity china;
    private OccupationEntity mongolia;
    private OccupationEntity japan;
    private OccupationEntity irkutsk;
    private OccupationEntity yakutsk;
    private OccupationEntity kamchatka;
    private OccupationEntity siberia;
    private OccupationEntity afghanistan;
    private OccupationEntity ural;
    private OccupationEntity middleEast;

    List<OccupationEntity> northAmericanCountries;
    List<OccupationEntity> southAmericanCountries;
    List<OccupationEntity> africanCountries;
    List<OccupationEntity> europeCountries;
    List<OccupationEntity> australianCountries;
    List<OccupationEntity> asianCountries;

    List<OccupationEntity> occupations;

    private Game(World world) {
        this.world = world;
        generate();
    }

    public static Game createWithoutIds(World world) {
        return new Game(world);
    }

    public static Game createWithIds(World world) {
        val game = createWithoutIds(world);
        game.setIds();
        return game;
    }

    private void setIds() {
        users.forEach(userGenerator::create);
        gameGenerator.create(game);
        players.forEach(playerGenerator::create);
        occupations.forEach(occupationGenerator::create);
    }

    private void generate() {
        generateGame();
        generateOccupations();
    }

    private void generateGame() {
        generateUsers();
        game = new GameEntity("newGame", userOne, world.getMap());
        generatePlayers();
        game.setPlayers(new HashSet<>(players));
        game.setActivePlayer(playerOne);
    }

    private void generateUsers() {
        userOne = new UserAccountEntity("userOne");
        userTwo = new UserAccountEntity("userTwo");
        userThree = new UserAccountEntity("userThree");
        userFour = new UserAccountEntity("userFour");
        users = Arrays.asList(userOne, userTwo, userThree, userFour);
    }

    private void generatePlayers() {
        playerOne = new PlayerEntity(game, userOne, 0, PlayerColor.values()[0], 10);
        playerTwo = new PlayerEntity(game, userTwo, 1, PlayerColor.values()[1], 10);
        playerThree = new PlayerEntity(game, userThree, 2, PlayerColor.values()[2], 10);
        playerFour = new PlayerEntity(game, userFour, 3, PlayerColor.values()[3], 10);
        players = Arrays.asList(playerOne, playerTwo, playerThree, playerFour);
    }

    private void generateOccupations() {
        alaska = new OccupationEntity(game, world.getAlaska(), playerOne, 5, 0);
        northwestTerritory = new OccupationEntity(game, world.getNorthwestTerritory(), playerOne, 1,
            0);
        greenland = new OccupationEntity(game, world.getGreenland(), playerOne, 2, 0);
        alberta = new OccupationEntity(game, world.getAlberta(), playerOne, 1, 0);
        ontario = new OccupationEntity(game, world.getOntario(), playerOne, 3, 0);
        quebec = new OccupationEntity(game, world.getQuebec(), playerOne, 4, 0);
        westernUnitedStates = new OccupationEntity(game, world.getWesternUnitedStates(), playerOne,
            4, 0);
        easternUnitedStates = new OccupationEntity(game, world.getEasternUnitedStates(), playerOne,
            5, 0);
        centralAmerica = new OccupationEntity(game, world.getCentralAmerica(), playerTwo, 5, 0);
        northAmericanCountries = Arrays
            .asList(alaska, northwestTerritory, greenland, alberta, ontario, quebec,
                westernUnitedStates, easternUnitedStates, centralAmerica);

        venezuela = new OccupationEntity(game, world.getVenezuela(), playerOne, 2, 0);
        peru = new OccupationEntity(game, world.getPeru(), playerTwo, 9, 0);
        brazil = new OccupationEntity(game, world.getBrazil(), playerTwo, 3, 0);
        argentina = new OccupationEntity(game, world.getArgentina(), playerTwo, 4, 0);
        southAmericanCountries = Arrays.asList(venezuela, peru, brazil, argentina);

        northAfrica = new OccupationEntity(game, world.getNorthAfrica(), playerThree, 5, 0);
        egypt = new OccupationEntity(game, world.getEgypt(), playerThree, 1, 0);
        eastAfrica = new OccupationEntity(game, world.getEastAfrica(), playerThree, 7, 0);
        congo = new OccupationEntity(game, world.getCongo(), playerThree, 2, 0);
        southAfrica = new OccupationEntity(game, world.getSouthAfrica(), playerThree, 2, 0);
        madagascar = new OccupationEntity(game, world.getMadagascar(), playerThree, 3, 0);
        africanCountries = Arrays
            .asList(northAfrica, egypt, eastAfrica, congo, southAfrica, madagascar);

        iceland = new OccupationEntity(game, world.getIceland(), playerOne, 1, 0);
        scandinavia = new OccupationEntity(game, world.getScandinavia(), playerThree, 6, 0);
        ukraine = new OccupationEntity(game, world.getUkraine(), playerFour, 4, 0);
        greatBritain = new OccupationEntity(game, world.getGreatBritain(), playerFour, 3, 0);
        northernEurope = new OccupationEntity(game, world.getNorthernEurope(), playerFour, 2, 0);
        southernEurope = new OccupationEntity(game, world.getSouthernEurope(), playerFour, 1, 0);
        westernEurope = new OccupationEntity(game, world.getWesternEurope(), playerFour, 1, 0);
        europeCountries = Arrays
            .asList(iceland, scandinavia, ukraine, greatBritain, northernEurope, southernEurope,
                westernEurope);

        indonesia = new OccupationEntity(game, world.getIndonesia(), playerOne, 1, 0);
        newGuinea = new OccupationEntity(game, world.getNewGuinea(), playerOne, 5, 0);
        westernAustralia = new OccupationEntity(game, world.getWesternAustralia(), playerOne, 3, 0);
        easternAustralia = new OccupationEntity(game, world.getEasternAustralia(), playerOne, 4, 0);
        australianCountries = Arrays
            .asList(indonesia, newGuinea, westernAustralia, easternAustralia);

        siam = new OccupationEntity(game, world.getSiam(), playerOne, 6, 0);
        india = new OccupationEntity(game, world.getIndia(), playerOne, 1, 0);
        china = new OccupationEntity(game, world.getChina(), playerTwo, 1, 0);
        mongolia = new OccupationEntity(game, world.getMongolia(), playerTwo, 3, 0);
        japan = new OccupationEntity(game, world.getJapan(), playerTwo, 3, 0);
        irkutsk = new OccupationEntity(game, world.getIrkutsk(), playerTwo, 2, 0);
        yakutsk = new OccupationEntity(game, world.getYakutsk(), playerTwo, 5, 0);
        kamchatka = new OccupationEntity(game, world.getKamchatka(), playerThree, 1, 0);
        siberia = new OccupationEntity(game, world.getSiberia(), playerThree, 1, 0);
        afghanistan = new OccupationEntity(game, world.getAfghanistan(), playerThree, 7, 0);
        ural = new OccupationEntity(game, world.getUral(), playerFour, 6, 0);
        middleEast = new OccupationEntity(game, world.getMiddleEast(), playerFour, 5, 0);
        asianCountries = Arrays
            .asList(siam, india, china, mongolia, japan, irkutsk, yakutsk, kamchatka, siberia,
                afghanistan, ural, middleEast);

        occupations = Stream
            .of(northAmericanCountries, southAmericanCountries, africanCountries, europeCountries,
                australianCountries, asianCountries).flatMap(Collection::stream)
            .collect(Collectors.toList());
        game.setOccupations(new HashSet<>(occupations));
    }
}