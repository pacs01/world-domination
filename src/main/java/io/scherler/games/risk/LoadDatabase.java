package io.scherler.games.risk;

import io.scherler.games.risk.entities.ContinentEntity;
import io.scherler.games.risk.entities.repositories.ContinentRepository;
import io.scherler.games.risk.entities.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.TerritoryRepository;
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

    // todo move database initialization to Liquibase
    @Bean
    CommandLineRunner initDatabase(ContinentRepository continentRepository, TerritoryRepository territoryRepository) {
        return args -> {
            log.info("Preloading entities...");

            log.info("Creating continents...");
            val northAmerica = new ContinentEntity("North America");
            val southAmerica = new ContinentEntity("South America");
            val europe = new ContinentEntity("Europe");
            val asia = new ContinentEntity("Asia");
            val australia = new ContinentEntity("Australia");
            val africa = new ContinentEntity("Africa");
            List<ContinentEntity> continents = Arrays.asList(northAmerica, southAmerica, europe, asia, australia, africa);
            continentRepository.saveAll(continents);

            log.info("Creating territories...");
            val alaska = new TerritoryEntity("Alaska", northAmerica);
            val northwestTerritory = new TerritoryEntity("Northwest Territory", northAmerica);
            val greenland = new TerritoryEntity("Greenland", northAmerica);
            val alberta = new TerritoryEntity("Alberta", northAmerica);
            val ontario = new TerritoryEntity("Ontario", northAmerica);
            val quebec = new TerritoryEntity("Quebec", northAmerica);
            val westernUnitedStates = new TerritoryEntity("Western United States", northAmerica);
            val easternUnitedStates = new TerritoryEntity("Eastern United States", northAmerica);
            val centralAmerica = new TerritoryEntity("Central America", northAmerica);
            List<TerritoryEntity> northAmericanCountries = Arrays.asList(alaska, northwestTerritory, greenland, alberta, ontario, quebec, westernUnitedStates, easternUnitedStates,
                centralAmerica);
            territoryRepository.saveAll(northAmericanCountries);

            val venezuela = new TerritoryEntity("Venezuela", southAmerica);
            val peru = new TerritoryEntity("Peru", southAmerica);
            val brazil = new TerritoryEntity("Brazil", southAmerica);
            val argentina = new TerritoryEntity("Argentina", southAmerica);
            List<TerritoryEntity> southAmericanCountries = Arrays.asList(venezuela, peru, brazil, argentina);
            territoryRepository.saveAll(southAmericanCountries);

            val northAfrica = new TerritoryEntity("North Africa", africa);
            val egypt = new TerritoryEntity("Egypt", africa);
            val eastAfrica = new TerritoryEntity("East Africa", africa);
            val congo = new TerritoryEntity("Congo", africa);
            val southAfrica = new TerritoryEntity("South Africa", africa);
            val madagascar = new TerritoryEntity("Madagascar", africa);
            List<TerritoryEntity> africanCountries = Arrays.asList(northAfrica, egypt, eastAfrica, congo, southAfrica, madagascar);
            territoryRepository.saveAll(africanCountries);

            val iceland = new TerritoryEntity("Iceland", europe);
            val scandinavia = new TerritoryEntity("Scandinavia", europe);
            val ukraine = new TerritoryEntity("Ukraine", europe);
            val greatBritian = new TerritoryEntity("Great Britian", europe);
            val northernEurope = new TerritoryEntity("Northern Europe", europe);
            val southernEurope = new TerritoryEntity("Southern Europe", europe);
            val westernEurope = new TerritoryEntity("Western Europe", europe);
            List<TerritoryEntity> europeCountries = Arrays.asList(iceland, scandinavia, ukraine, greatBritian, northernEurope, southernEurope, westernEurope);
            territoryRepository.saveAll(europeCountries);

            val indonesia = new TerritoryEntity("Indonesia", australia);
            val newGuinea = new TerritoryEntity("New Guinea", australia);
            val westernAustralia = new TerritoryEntity("Western Australia", australia);
            val easternAustralia = new TerritoryEntity("Eastern Australia", australia);
            List<TerritoryEntity> australianCountries = Arrays.asList(indonesia, newGuinea, westernAustralia, easternAustralia);
            territoryRepository.saveAll(australianCountries);

            val siam = new TerritoryEntity("Siam", asia);
            val india = new TerritoryEntity("India", asia);
            val china = new TerritoryEntity("China", asia);
            val mongolia = new TerritoryEntity("Mongolia", asia);
            val japan = new TerritoryEntity("Japan", asia);
            val irkutsk = new TerritoryEntity("Irkutsk", asia);
            val yakutsk = new TerritoryEntity("Yakutsk", asia);
            val kamchatka = new TerritoryEntity("Kamchatka", asia);
            val siberia = new TerritoryEntity("Siberia", asia);
            val afghanistan = new TerritoryEntity("Afghanistan", asia);
            val ural = new TerritoryEntity("Ural", asia);
            val middleEast = new TerritoryEntity("Middle East", asia);
            List<TerritoryEntity> asianCountries = Arrays.asList(siam, india, china, mongolia, japan, irkutsk, yakutsk, kamchatka, siberia, afghanistan, ural, middleEast);
            territoryRepository.saveAll(asianCountries);

            log.info("Defining adjacent territories...");
            alaska.addAdjacentTerritories(kamchatka, northwestTerritory, alberta);
            northwestTerritory.addAdjacentTerritories(alaska, greenland, alberta, ontario);
            greenland.addAdjacentTerritories(northwestTerritory, ontario, quebec, iceland);
            alberta.addAdjacentTerritories(alaska, northwestTerritory, ontario, westernUnitedStates);
            ontario.addAdjacentTerritories(northwestTerritory, alberta, westernUnitedStates, quebec, easternUnitedStates, greenland);
            quebec.addAdjacentTerritories(ontario, greenland, easternUnitedStates);
            westernUnitedStates.addAdjacentTerritories(alberta, ontario, easternUnitedStates, centralAmerica);
            easternUnitedStates.addAdjacentTerritories(quebec, ontario, westernUnitedStates, centralAmerica);
            centralAmerica.addAdjacentTerritories(westernUnitedStates, easternUnitedStates, venezuela);
            territoryRepository.saveAll(northAmericanCountries);

            venezuela.addAdjacentTerritories(centralAmerica, peru, brazil);
            peru.addAdjacentTerritories(venezuela, brazil, argentina);
            brazil.addAdjacentTerritories(venezuela, peru, argentina, northAfrica);
            argentina.addAdjacentTerritories(peru, brazil);
            territoryRepository.saveAll(southAmericanCountries);

            northAfrica.addAdjacentTerritories(brazil, westernEurope, southernEurope, egypt, eastAfrica, congo);
            egypt.addAdjacentTerritories(southernEurope, northAfrica, eastAfrica, middleEast);
            eastAfrica.addAdjacentTerritories(egypt, northAfrica, congo, southAfrica, middleEast, madagascar);
            congo.addAdjacentTerritories(northAfrica, eastAfrica, southAfrica);
            southAfrica.addAdjacentTerritories(congo, eastAfrica, madagascar);
            madagascar.addAdjacentTerritories(southAfrica, eastAfrica);
            territoryRepository.saveAll(africanCountries);

            iceland.addAdjacentTerritories(greenland, greatBritian, scandinavia);
            scandinavia.addAdjacentTerritories(iceland, greatBritian, northernEurope, ukraine);
            ukraine.addAdjacentTerritories(scandinavia, northernEurope, southernEurope, ural, afghanistan, middleEast);
            greatBritian.addAdjacentTerritories(iceland, scandinavia, northernEurope, westernEurope);
            northernEurope.addAdjacentTerritories(greatBritian, scandinavia, ukraine, southernEurope, westernEurope);
            southernEurope.addAdjacentTerritories(westernEurope, northernEurope, ukraine, middleEast, egypt, northAfrica);
            westernEurope.addAdjacentTerritories(greatBritian, northernEurope, southernEurope, northAfrica);
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
            middleEast.addAdjacentTerritories(ukraine, southernEurope, egypt, eastAfrica, india, afghanistan);
            territoryRepository.saveAll(asianCountries);

            log.info("Preloading done...");
        };
    }

}
