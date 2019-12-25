package io.scherler.games.risk;

import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.map.ContinentEntity;
import io.scherler.games.risk.entities.map.MapEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.identity.UserAccountRepository;
import io.scherler.games.risk.entities.repositories.map.ContinentRepository;
import io.scherler.games.risk.entities.repositories.map.MapRepository;
import io.scherler.games.risk.entities.repositories.map.TerritoryRepository;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    public static final String TEST_MAP_NAME = "helloworld";

    // todo move database initialization to Liquibase
    @Bean
    CommandLineRunner initDatabase(UserAccountRepository userAccountRepository,
        MapRepository mapRepository, ContinentRepository continentRepository,
        TerritoryRepository territoryRepository) {
        return args -> {
            log.info("Preloading entities...");

            log.info("Creating userAccount...");
            val userAccount = new UserAccountEntity("testadmin");
            userAccountRepository.save(userAccount);

            log.info("Creating map...");
            val map = new MapEntity(TEST_MAP_NAME, userAccount);
            mapRepository.save(map);

            log.info("Creating continents...");
            val northAmerica = new ContinentEntity(map, "North America");
            val southAmerica = new ContinentEntity(map, "South America");
            val europe = new ContinentEntity(map, "Europe");
            val asia = new ContinentEntity(map, "Asia");
            val australia = new ContinentEntity(map, "Australia");
            val africa = new ContinentEntity(map, "Africa");
            List<ContinentEntity> continents = Arrays
                .asList(northAmerica, southAmerica, europe, asia, australia, africa);
            continentRepository.saveAll(continents);

            log.info("Creating territories...");
            val alaska = new TerritoryEntity("Alaska", map, northAmerica);
            val northwestTerritory = new TerritoryEntity("Northwest Territory", map, northAmerica);
            val greenland = new TerritoryEntity("Greenland", map, northAmerica);
            val alberta = new TerritoryEntity("Alberta", map, northAmerica);
            val ontario = new TerritoryEntity("Ontario", map, northAmerica);
            val quebec = new TerritoryEntity("Quebec", map, northAmerica);
            val westernUnitedStates = new TerritoryEntity("Western United States", map,
                northAmerica);
            val easternUnitedStates = new TerritoryEntity("Eastern United States", map,
                northAmerica);
            val centralAmerica = new TerritoryEntity("Central America", map, northAmerica);
            List<TerritoryEntity> northAmericanCountries = Arrays
                .asList(alaska, northwestTerritory, greenland, alberta, ontario, quebec,
                    westernUnitedStates, easternUnitedStates,
                    centralAmerica);
            territoryRepository.saveAll(northAmericanCountries);

            val venezuela = new TerritoryEntity("Venezuela", map, southAmerica);
            val peru = new TerritoryEntity("Peru", map, southAmerica);
            val brazil = new TerritoryEntity("Brazil", map, southAmerica);
            val argentina = new TerritoryEntity("Argentina", map, southAmerica);
            List<TerritoryEntity> southAmericanCountries = Arrays
                .asList(venezuela, peru, brazil, argentina);
            territoryRepository.saveAll(southAmericanCountries);

            val northAfrica = new TerritoryEntity("North Africa", map, africa);
            val egypt = new TerritoryEntity("Egypt", map, africa);
            val eastAfrica = new TerritoryEntity("East Africa", map, africa);
            val congo = new TerritoryEntity("Congo", map, africa);
            val southAfrica = new TerritoryEntity("South Africa", map, africa);
            val madagascar = new TerritoryEntity("Madagascar", map, africa);
            List<TerritoryEntity> africanCountries = Arrays
                .asList(northAfrica, egypt, eastAfrica, congo, southAfrica, madagascar);
            territoryRepository.saveAll(africanCountries);

            val iceland = new TerritoryEntity("Iceland", map, europe);
            val scandinavia = new TerritoryEntity("Scandinavia", map, europe);
            val ukraine = new TerritoryEntity("Ukraine", map, europe);
            val greatBritain = new TerritoryEntity("Great Britain", map, europe);
            val northernEurope = new TerritoryEntity("Northern Europe", map, europe);
            val southernEurope = new TerritoryEntity("Southern Europe", map, europe);
            val westernEurope = new TerritoryEntity("Western Europe", map, europe);
            List<TerritoryEntity> europeCountries = Arrays
                .asList(iceland, scandinavia, ukraine, greatBritain, northernEurope, southernEurope,
                    westernEurope);
            territoryRepository.saveAll(europeCountries);

            val indonesia = new TerritoryEntity("Indonesia", map, australia);
            val newGuinea = new TerritoryEntity("New Guinea", map, australia);
            val westernAustralia = new TerritoryEntity("Western Australia", map, australia);
            val easternAustralia = new TerritoryEntity("Eastern Australia", map, australia);
            List<TerritoryEntity> australianCountries = Arrays
                .asList(indonesia, newGuinea, westernAustralia, easternAustralia);
            territoryRepository.saveAll(australianCountries);

            val siam = new TerritoryEntity("Siam", map, asia);
            val india = new TerritoryEntity("India", map, asia);
            val china = new TerritoryEntity("China", map, asia);
            val mongolia = new TerritoryEntity("Mongolia", map, asia);
            val japan = new TerritoryEntity("Japan", map, asia);
            val irkutsk = new TerritoryEntity("Irkutsk", map, asia);
            val yakutsk = new TerritoryEntity("Yakutsk", map, asia);
            val kamchatka = new TerritoryEntity("Kamchatka", map, asia);
            val siberia = new TerritoryEntity("Siberia", map, asia);
            val afghanistan = new TerritoryEntity("Afghanistan", map, asia);
            val ural = new TerritoryEntity("Ural", map, asia);
            val middleEast = new TerritoryEntity("Middle East", map, asia);
            List<TerritoryEntity> asianCountries = Arrays
                .asList(siam, india, china, mongolia, japan, irkutsk, yakutsk, kamchatka, siberia,
                    afghanistan, ural, middleEast);
            territoryRepository.saveAll(asianCountries);

            log.info("Defining adjacent territories...");
            alaska.addAdjacentTerritories(kamchatka, northwestTerritory, alberta);
            northwestTerritory.addAdjacentTerritories(alaska, greenland, alberta, ontario);
            greenland.addAdjacentTerritories(northwestTerritory, ontario, quebec, iceland);
            alberta
                .addAdjacentTerritories(alaska, northwestTerritory, ontario, westernUnitedStates);
            ontario.addAdjacentTerritories(northwestTerritory, alberta, westernUnitedStates, quebec,
                easternUnitedStates, greenland);
            quebec.addAdjacentTerritories(ontario, greenland, easternUnitedStates);
            westernUnitedStates
                .addAdjacentTerritories(alberta, ontario, easternUnitedStates, centralAmerica);
            easternUnitedStates
                .addAdjacentTerritories(quebec, ontario, westernUnitedStates, centralAmerica);
            centralAmerica
                .addAdjacentTerritories(westernUnitedStates, easternUnitedStates, venezuela);
            territoryRepository.saveAll(northAmericanCountries);

            venezuela.addAdjacentTerritories(centralAmerica, peru, brazil);
            peru.addAdjacentTerritories(venezuela, brazil, argentina);
            brazil.addAdjacentTerritories(venezuela, peru, argentina, northAfrica);
            argentina.addAdjacentTerritories(peru, brazil);
            territoryRepository.saveAll(southAmericanCountries);

            northAfrica
                .addAdjacentTerritories(brazil, westernEurope, southernEurope, egypt, eastAfrica,
                    congo);
            egypt.addAdjacentTerritories(southernEurope, northAfrica, eastAfrica, middleEast);
            eastAfrica.addAdjacentTerritories(egypt, northAfrica, congo, southAfrica, middleEast,
                madagascar);
            congo.addAdjacentTerritories(northAfrica, eastAfrica, southAfrica);
            southAfrica.addAdjacentTerritories(congo, eastAfrica, madagascar);
            madagascar.addAdjacentTerritories(southAfrica, eastAfrica);
            territoryRepository.saveAll(africanCountries);

            iceland.addAdjacentTerritories(greenland, greatBritain, scandinavia);
            scandinavia.addAdjacentTerritories(iceland, greatBritain, northernEurope, ukraine);
            ukraine.addAdjacentTerritories(scandinavia, northernEurope, southernEurope, ural,
                afghanistan, middleEast);
            greatBritain
                .addAdjacentTerritories(iceland, scandinavia, northernEurope, westernEurope);
            northernEurope
                .addAdjacentTerritories(greatBritain, scandinavia, ukraine, southernEurope,
                    westernEurope);
            southernEurope
                .addAdjacentTerritories(westernEurope, northernEurope, ukraine, middleEast, egypt,
                    northAfrica);
            westernEurope
                .addAdjacentTerritories(greatBritain, northernEurope, southernEurope, northAfrica);
            territoryRepository.saveAll(europeCountries);

            indonesia.addAdjacentTerritories(siam, newGuinea, westernAustralia);
            newGuinea.addAdjacentTerritories(indonesia, westernAustralia, easternAustralia);
            westernAustralia.addAdjacentTerritories(indonesia, newGuinea, easternAustralia);
            easternAustralia.addAdjacentTerritories(westernAustralia, newGuinea);
            territoryRepository.saveAll(australianCountries);

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
            middleEast.addAdjacentTerritories(ukraine, southernEurope, egypt, eastAfrica, india,
                afghanistan);
            territoryRepository.saveAll(asianCountries);

            log.info("Preloading done...");
        };
    }

}
