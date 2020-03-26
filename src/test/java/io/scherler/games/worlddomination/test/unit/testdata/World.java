package io.scherler.games.worlddomination.test.unit.testdata;

import static io.scherler.games.worlddomination.test.unit.testdata.Game.Users.USER_ONE;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Continents.AFRICA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Continents.ASIA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Continents.AUSTRALIA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Continents.EUROPE;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Continents.NORTH_AMERICA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Continents.SOUTH_AMERICA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Maps.HELLO_WORLD;

import io.scherler.games.worlddomination.LoadDatabase;
import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import io.scherler.games.worlddomination.entities.map.ContinentEntity;
import io.scherler.games.worlddomination.entities.map.MapEntity;
import io.scherler.games.worlddomination.entities.map.TerritoryEntity;
import io.scherler.games.worlddomination.test.unit.testdata.Game.Games;
import io.scherler.games.worlddomination.test.unit.testdata.Game.Occupations;
import io.scherler.games.worlddomination.test.unit.testdata.Game.Users;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.val;

@Getter
public class World {

    public enum Maps {

        HELLO_WORLD(LoadDatabase.TEST_MAP_NAME, USER_ONE);

        private final String name;
        private final Users creator;

        Maps(String name, Users creator) {
            this.name = name;
            this.creator = creator;
        }

        MapEntity toUpstreamEntity() {
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

        ContinentEntity toUpstreamEntity() {
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

    public enum Territories {
        ALASKA(HELLO_WORLD, "Alaska", NORTH_AMERICA),
        NORTHWEST_TERRITORY(HELLO_WORLD, "Northwest Territory", NORTH_AMERICA, ALASKA),
        GREENLAND(HELLO_WORLD, "Greenland", NORTH_AMERICA, NORTHWEST_TERRITORY),
        ALBERTA(HELLO_WORLD, "Alberta", NORTH_AMERICA, ALASKA, NORTHWEST_TERRITORY),
        ONTARIO(HELLO_WORLD, "Ontario", NORTH_AMERICA, NORTHWEST_TERRITORY, ALBERTA, GREENLAND),
        QUEBEC(HELLO_WORLD, "Quebec", NORTH_AMERICA, ONTARIO, GREENLAND),
        WESTERN_UNITED_STATES(HELLO_WORLD, "Western United States", NORTH_AMERICA, ALBERTA,
            ONTARIO),
        EASTERN_UNITED_STATES(HELLO_WORLD, "Eastern United States", NORTH_AMERICA, QUEBEC, ONTARIO,
            WESTERN_UNITED_STATES),
        CENTRAL_AMERICA(HELLO_WORLD, "Central America", NORTH_AMERICA, WESTERN_UNITED_STATES,
            EASTERN_UNITED_STATES),

        VENEZUELA(HELLO_WORLD, "Venezuela", SOUTH_AMERICA, CENTRAL_AMERICA),
        PERU(HELLO_WORLD, "Peru", SOUTH_AMERICA, VENEZUELA),
        BRAZIL(HELLO_WORLD, "Brazil", SOUTH_AMERICA, VENEZUELA, PERU),
        ARGENTINA(HELLO_WORLD, "Argentina", SOUTH_AMERICA, PERU, BRAZIL),

        NORTH_AFRICA(HELLO_WORLD, "North Africa", AFRICA, BRAZIL),
        EGYPT(HELLO_WORLD, "Egypt", AFRICA, NORTH_AFRICA),
        EAST_AFRICA(HELLO_WORLD, "East Africa", AFRICA, EGYPT, NORTH_AFRICA),
        CONGO(HELLO_WORLD, "Congo", AFRICA, NORTH_AFRICA, EAST_AFRICA),
        SOUTH_AFRICA(HELLO_WORLD, "South Africa", AFRICA, CONGO, EAST_AFRICA),
        MADAGASCAR(HELLO_WORLD, "Madagascar", AFRICA, SOUTH_AFRICA, EAST_AFRICA),

        ICELAND(HELLO_WORLD, "Iceland", EUROPE, GREENLAND),
        SCANDINAVIA(HELLO_WORLD, "Scandinavia", EUROPE, ICELAND),
        UKRAINE(HELLO_WORLD, "Ukraine", EUROPE, SCANDINAVIA),
        GREAT_BRITAIN(HELLO_WORLD, "Great Britain", EUROPE, ICELAND, SCANDINAVIA),
        NORTHERN_EUROPE(HELLO_WORLD, "Northern Europe", EUROPE, GREAT_BRITAIN, SCANDINAVIA,
            UKRAINE),
        SOUTHERN_EUROPE(HELLO_WORLD, "Southern Europe", EUROPE, NORTHERN_EUROPE, UKRAINE, EGYPT,
            NORTH_AFRICA),
        WESTERN_EUROPE(HELLO_WORLD, "Western Europe", EUROPE, GREAT_BRITAIN, NORTHERN_EUROPE,
            SOUTHERN_EUROPE, NORTH_AFRICA),

        INDONESIA(HELLO_WORLD, "Indonesia", AUSTRALIA),
        NEW_GUINEA(HELLO_WORLD, "New Guinea", AUSTRALIA, INDONESIA),
        WESTERN_AUSTRALIA(HELLO_WORLD, "Western Australia", AUSTRALIA, INDONESIA, NEW_GUINEA),
        EASTERN_AUSTRALIA(HELLO_WORLD, "Eastern Australia", AUSTRALIA, WESTERN_AUSTRALIA,
            NEW_GUINEA),

        SIAM(HELLO_WORLD, "Siam", ASIA, INDONESIA),
        INDIA(HELLO_WORLD, "India", ASIA, SIAM),
        CHINA(HELLO_WORLD, "China", ASIA, SIAM, INDIA),
        MONGOLIA(HELLO_WORLD, "Mongolia", ASIA, CHINA),
        JAPAN(HELLO_WORLD, "Japan", ASIA, MONGOLIA),
        IRKUTSK(HELLO_WORLD, "Irkutsk", ASIA, MONGOLIA),
        YAKUTSK(HELLO_WORLD, "Yakutsk", ASIA, IRKUTSK),
        KAMCHATKA(HELLO_WORLD, "Kamchatka", ASIA, ALASKA, YAKUTSK, IRKUTSK, MONGOLIA, JAPAN),
        SIBERIA(HELLO_WORLD, "Siberia", ASIA, CHINA, MONGOLIA, IRKUTSK, YAKUTSK),
        AFGHANISTAN(HELLO_WORLD, "Afghanistan", ASIA, UKRAINE, INDIA, CHINA),
        URAL(HELLO_WORLD, "Ural", ASIA, UKRAINE, AFGHANISTAN, CHINA, SIBERIA),
        MIDDLE_EAST(HELLO_WORLD, "Middle East", ASIA, UKRAINE, SOUTHERN_EUROPE, EGYPT, EAST_AFRICA,
            INDIA,
            AFGHANISTAN);

        private final String name;
        private final Maps map;
        private final Continents continent;
        private final Set<Territories> adjacentTerritories = new HashSet<>();

        Territories(Maps map, String name, Continents continent,
            Territories... adjacentTerritories) {
            this.name = name;
            this.map = map;
            this.continent = continent;
            Collections.addAll(this.adjacentTerritories, adjacentTerritories);
            this.adjacentTerritories.forEach(t -> t.addAdjacentTerritory(this));
        }

        public void addAdjacentTerritory(Territories territory) {
            adjacentTerritories.add(territory);
        }

        TerritoryEntity toUpstreamEntity() {
            val territory = new TerritoryEntity(name, map.toUpstreamEntity(), continent.toUpstreamEntity());
            territory.setId(Integer.toUnsignedLong(this.ordinal()));

            return territory;
        }

        public TerritoryEntity toEntity() {
            return toEntity(this.toUpstreamEntity());
        }

        public TerritoryEntity toEntity(TerritoryEntity territory) {
            loadAdjacentTerritories(this, territory);

            val occupations = Occupations.getAllForTerritory(territory);
            territory.setOccupations(occupations);

            return territory;
        }

        private static void loadAdjacentTerritories(Territories territory, TerritoryEntity territoryEntity) {
            val allEntities = allToMap();
            allEntities.replace(territory, territoryEntity);

            Arrays.stream(Territories.values())
                .forEach((t) -> {
                    val entity = allEntities.get(t);
                    val adjacentTerritories = t.adjacentTerritories.stream()
                        .map(allEntities::get)
                        .collect(Collectors.toSet());
                    entity.setAdjacentTerritories(adjacentTerritories);
                });
        }

        private static Map<Territories, TerritoryEntity> allToMap() {
            return Arrays.stream(Territories.values())
                .collect(Collectors.toMap(Function.identity(), Territories::toUpstreamEntity));
        }

        public static Set<TerritoryEntity> getAll() {
            return Arrays.stream(Territories.values())
                .map(Territories::toUpstreamEntity)
                .collect(Collectors.toSet());
        }

        private Set<Territories> getAdjacentTerritories(TerritoryEntity territory) {
            val adjacentTerritories = Arrays.stream(Territories.values())
                .filter(t -> t.adjacentTerritories.contains(this))
                .collect(Collectors.toSet());
            adjacentTerritories.addAll(this.adjacentTerritories);
            return adjacentTerritories;
        }

        public static Set<TerritoryEntity> getAllForContinent(ContinentEntity continent) {
            return Arrays.stream(Territories.values())
                .map(Territories::toUpstreamEntity)
                .filter(e -> e.getContinent().equals(continent))
                .peek(e -> e.setContinent(continent))
                .collect(Collectors.toSet());
        }

        public static Set<TerritoryEntity> getAllForMap(MapEntity map) {
            return Arrays.stream(Territories.values())
                .map(Territories::toUpstreamEntity)
                .filter(e -> e.getMap().equals(map))
                .peek(e -> e.setMap(map))
                .collect(Collectors.toSet());
        }
    }


}