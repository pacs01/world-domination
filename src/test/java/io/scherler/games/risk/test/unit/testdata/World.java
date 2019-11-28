package io.scherler.games.risk.test.unit.testdata;

import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.map.ContinentEntity;
import io.scherler.games.risk.entities.map.MapEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import lombok.Getter;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class World {

    private final MockEntityCreator<UserAccountEntity> userGenerator = new MockEntityCreator<>();
    private final MockEntityCreator<MapEntity> mapGenerator = new MockEntityCreator<>();
    private final MockEntityCreator<ContinentEntity> continentGenerator = new MockEntityCreator<>();
    private final MockEntityCreator<TerritoryEntity> territoryGenerator = new MockEntityCreator<>();

    private UserAccountEntity creator;

    private MapEntity map;

    private ContinentEntity northAmerica;
    private ContinentEntity southAmerica;
    private ContinentEntity europe;
    private ContinentEntity asia;
    private ContinentEntity australia;
    private ContinentEntity africa;
    private List<ContinentEntity> continents;

    private TerritoryEntity alaska;
    private TerritoryEntity northwestTerritory;
    private TerritoryEntity alberta;
    private TerritoryEntity ontario;
    private TerritoryEntity greenland;
    private TerritoryEntity quebec;
    private TerritoryEntity westernUnitedStates;
    private TerritoryEntity easternUnitedStates;
    private TerritoryEntity centralAmerica;
    private TerritoryEntity venezuela;
    private TerritoryEntity peru;
    private TerritoryEntity brazil;
    private TerritoryEntity argentina;
    private TerritoryEntity northAfrica;
    private TerritoryEntity egypt;
    private TerritoryEntity eastAfrica;
    private TerritoryEntity congo;
    private TerritoryEntity southAfrica;
    private TerritoryEntity madagascar;
    private TerritoryEntity iceland;
    private TerritoryEntity scandinavia;
    private TerritoryEntity ukraine;
    private TerritoryEntity greatBritain;
    private TerritoryEntity northernEurope;
    private TerritoryEntity southernEurope;
    private TerritoryEntity westernEurope;
    private TerritoryEntity indonesia;
    private TerritoryEntity newGuinea;
    private TerritoryEntity westernAustralia;
    private TerritoryEntity easternAustralia;
    private TerritoryEntity siam;
    private TerritoryEntity india;
    private TerritoryEntity china;
    private TerritoryEntity mongolia;
    private TerritoryEntity japan;
    private TerritoryEntity irkutsk;
    private TerritoryEntity yakutsk;
    private TerritoryEntity kamchatka;
    private TerritoryEntity siberia;
    private TerritoryEntity afghanistan;
    private TerritoryEntity ural;
    private TerritoryEntity middleEast;

    List<TerritoryEntity> northAmericanCountries;
    List<TerritoryEntity> southAmericanCountries;
    List<TerritoryEntity> africanCountries;
    List<TerritoryEntity> europeCountries;
    List<TerritoryEntity> australianCountries;
    List<TerritoryEntity> asianCountries;

    List<TerritoryEntity> territories;

    private World() {
        generate();
    }

    public static World createWithoutIds() {
        return new World();
    }

    public static World createWithIds() {
        val world = createWithoutIds();
        world.setIds();
        return world;
    }

    private void setIds() {
        userGenerator.create(creator);
        mapGenerator.create(map);
        continents.forEach(continentGenerator::create);
        territories.forEach(territoryGenerator::create);
    }

    private void generate() {
        generateUser();
        generateMap();
        generateContinents();
        generateTerritories();
        setConnections();
    }

    private void generateUser() {
        creator = new UserAccountEntity("creator");
    }

    private void generateMap() {
        map = new MapEntity("helloworld", creator);
    }

    private void generateContinents() {
        northAmerica = new ContinentEntity(map, "North America");
        southAmerica = new ContinentEntity(map, "South America");
        europe = new ContinentEntity(map, "Europe");
        asia = new ContinentEntity(map, "Asia");
        australia = new ContinentEntity(map, "Australia");
        africa = new ContinentEntity(map, "Africa");
        continents = Arrays.asList(northAmerica, southAmerica, europe, asia, australia, africa);
    }

    private void generateTerritories() {
        alaska = new TerritoryEntity("Alaska", northAmerica);
        northwestTerritory = new TerritoryEntity("Northwest Territory", northAmerica);
        greenland = new TerritoryEntity("Greenland", northAmerica);
        alberta = new TerritoryEntity("Alberta", northAmerica);
        ontario = new TerritoryEntity("Ontario", northAmerica);
        quebec = new TerritoryEntity("Quebec", northAmerica);
        westernUnitedStates = new TerritoryEntity("Western United States", northAmerica);
        easternUnitedStates = new TerritoryEntity("Eastern United States", northAmerica);
        centralAmerica = new TerritoryEntity("Central America", northAmerica);
        northAmericanCountries = Arrays.asList(alaska, northwestTerritory, greenland, alberta, ontario, quebec, westernUnitedStates, easternUnitedStates, centralAmerica);
        northAmerica.setTerritories(new HashSet<>(northAmericanCountries));

        venezuela = new TerritoryEntity("Venezuela", southAmerica);
        peru = new TerritoryEntity("Peru", southAmerica);
        brazil = new TerritoryEntity("Brazil", southAmerica);
        argentina = new TerritoryEntity("Argentina", southAmerica);
        southAmericanCountries = Arrays.asList(venezuela, peru, brazil, argentina);
        southAmerica.setTerritories(new HashSet<>(southAmericanCountries));

        northAfrica = new TerritoryEntity("North Africa", africa);
        egypt = new TerritoryEntity("Egypt", africa);
        eastAfrica = new TerritoryEntity("East Africa", africa);
        congo = new TerritoryEntity("Congo", africa);
        southAfrica = new TerritoryEntity("South Africa", africa);
        madagascar = new TerritoryEntity("Madagascar", africa);
        africanCountries = Arrays.asList(northAfrica, egypt, eastAfrica, congo, southAfrica, madagascar);
        africa.setTerritories(new HashSet<>(africanCountries));

        iceland = new TerritoryEntity("Iceland", europe);
        scandinavia = new TerritoryEntity("Scandinavia", europe);
        ukraine = new TerritoryEntity("Ukraine", europe);
        greatBritain = new TerritoryEntity("Great Britain", europe);
        northernEurope = new TerritoryEntity("Northern Europe", europe);
        southernEurope = new TerritoryEntity("Southern Europe", europe);
        westernEurope = new TerritoryEntity("Western Europe", europe);
        europeCountries = Arrays.asList(iceland, scandinavia, ukraine, greatBritain, northernEurope, southernEurope, westernEurope);
        europe.setTerritories(new HashSet<>(europeCountries));

        indonesia = new TerritoryEntity("Indonesia", australia);
        newGuinea = new TerritoryEntity("New Guinea", australia);
        westernAustralia = new TerritoryEntity("Western Australia", australia);
        easternAustralia = new TerritoryEntity("Eastern Australia", australia);
        australianCountries = Arrays.asList(indonesia, newGuinea, westernAustralia, easternAustralia);
        australia.setTerritories(new HashSet<>(australianCountries));

        siam = new TerritoryEntity("Siam", asia);
        india = new TerritoryEntity("India", asia);
        china = new TerritoryEntity("China", asia);
        mongolia = new TerritoryEntity("Mongolia", asia);
        japan = new TerritoryEntity("Japan", asia);
        irkutsk = new TerritoryEntity("Irkutsk", asia);
        yakutsk = new TerritoryEntity("Yakutsk", asia);
        kamchatka = new TerritoryEntity("Kamchatka", asia);
        siberia = new TerritoryEntity("Siberia", asia);
        afghanistan = new TerritoryEntity("Afghanistan", asia);
        ural = new TerritoryEntity("Ural", asia);
        middleEast = new TerritoryEntity("Middle East", asia);
        asianCountries = Arrays.asList(siam, india, china, mongolia, japan, irkutsk, yakutsk, kamchatka, siberia, afghanistan, ural, middleEast);
        asia.setTerritories(new HashSet<>(asianCountries));

        territories = Stream.of(northAmericanCountries, southAmericanCountries, africanCountries, europeCountries, australianCountries, asianCountries).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private void setConnections() {
        alaska.addAdjacentTerritories(kamchatka, northwestTerritory, alberta);
        northwestTerritory.addAdjacentTerritories(alaska, greenland, alberta, ontario);
        greenland.addAdjacentTerritories(northwestTerritory, ontario, quebec, iceland);
        alberta.addAdjacentTerritories(alaska, northwestTerritory, ontario, westernUnitedStates);
        ontario.addAdjacentTerritories(northwestTerritory, alberta, westernUnitedStates, quebec, easternUnitedStates, greenland);
        quebec.addAdjacentTerritories(ontario, greenland, easternUnitedStates);
        westernUnitedStates.addAdjacentTerritories(alberta, ontario, easternUnitedStates, centralAmerica);
        easternUnitedStates.addAdjacentTerritories(quebec, ontario, westernUnitedStates, centralAmerica);
        centralAmerica.addAdjacentTerritories(westernUnitedStates, easternUnitedStates, venezuela);

        venezuela.addAdjacentTerritories(centralAmerica, peru, brazil);
        peru.addAdjacentTerritories(venezuela, brazil, argentina);
        brazil.addAdjacentTerritories(venezuela, peru, argentina, northAfrica);
        argentina.addAdjacentTerritories(peru, brazil);

        northAfrica.addAdjacentTerritories(brazil, westernEurope, southernEurope, egypt, eastAfrica, congo);
        egypt.addAdjacentTerritories(southernEurope, northAfrica, eastAfrica, middleEast);
        eastAfrica.addAdjacentTerritories(egypt, northAfrica, congo, southAfrica, middleEast, madagascar);
        congo.addAdjacentTerritories(northAfrica, eastAfrica, southAfrica);
        southAfrica.addAdjacentTerritories(congo, eastAfrica, madagascar);
        madagascar.addAdjacentTerritories(southAfrica, eastAfrica);

        iceland.addAdjacentTerritories(greenland, greatBritain, scandinavia);
        scandinavia.addAdjacentTerritories(iceland, greatBritain, northernEurope, ukraine);
        ukraine.addAdjacentTerritories(scandinavia, northernEurope, southernEurope, ural, afghanistan, middleEast);
        greatBritain.addAdjacentTerritories(iceland, scandinavia, northernEurope, westernEurope);
        northernEurope.addAdjacentTerritories(greatBritain, scandinavia, ukraine, southernEurope, westernEurope);
        southernEurope.addAdjacentTerritories(westernEurope, northernEurope, ukraine, middleEast, egypt, northAfrica);
        westernEurope.addAdjacentTerritories(greatBritain, northernEurope, southernEurope, northAfrica);

        indonesia.addAdjacentTerritories(siam, newGuinea, westernAustralia);
        newGuinea.addAdjacentTerritories(indonesia, westernAustralia, easternAustralia);
        westernAustralia.addAdjacentTerritories(indonesia, newGuinea, easternAustralia);
        easternAustralia.addAdjacentTerritories(westernAustralia, newGuinea);

        siam.addAdjacentTerritories(indonesia, india, china);
        india.addAdjacentTerritories(siam, china, afghanistan, middleEast);
        china.addAdjacentTerritories(siam, india, afghanistan, ural, siberia, mongolia);
        mongolia.addAdjacentTerritories(china, siberia, irkutsk, kamchatka, japan);
        japan.addAdjacentTerritories(kamchatka, mongolia);
        irkutsk.addAdjacentTerritories(mongolia, siberia, yakutsk, kamchatka);
        yakutsk.addAdjacentTerritories(siberia, irkutsk, kamchatka);
        kamchatka.addAdjacentTerritories(alaska, yakutsk, irkutsk, mongolia, japan);
        siberia.addAdjacentTerritories(ural, china, mongolia, irkutsk, yakutsk);
        afghanistan.addAdjacentTerritories(ukraine, middleEast, india, china, ural);
        ural.addAdjacentTerritories(ukraine, afghanistan, china, siberia);
        middleEast.addAdjacentTerritories(ukraine, southernEurope, egypt, eastAfrica, india, afghanistan);
    }
}