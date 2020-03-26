package io.scherler.games.worlddomination.test.unit;

import static io.scherler.games.worlddomination.test.unit.testdata.game.Players.PLAYER_ONE;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.ALASKA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.ARGENTINA;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.EASTERN_UNITED_STATES;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.ICELAND;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.PERU;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.WESTERN_EUROPE;
import static io.scherler.games.worlddomination.test.unit.testdata.map.Territories.WESTERN_UNITED_STATES;

import io.scherler.games.worlddomination.entities.game.OccupationEntity;
import io.scherler.games.worlddomination.entities.map.TerritoryEntity;
import io.scherler.games.worlddomination.entities.repositories.map.TerritoryRepository;
import io.scherler.games.worlddomination.services.map.TerritoryService;
import io.scherler.games.worlddomination.test.unit.testdata.game.Occupations;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class ConnectedTerritoryValidationTest {

    @Mock
    private TerritoryRepository territoryRepository;

    private TerritoryService territoryService = new TerritoryService(territoryRepository);

    private List<TerritoryEntity> territoriesOfPlayerOne;

    @BeforeEach
    void setUp() {
        territoriesOfPlayerOne = Occupations.getAllForPlayer(PLAYER_ONE.toEntity()).stream()
            .map(OccupationEntity::getTerritory)
            .collect(Collectors.toList());
    }

    @Test
    void testConnectedTerritories() {
        Assertions.assertTrue(territoryService
            .areConnected(ALASKA.toEntity(), EASTERN_UNITED_STATES.toEntity(),
                territoriesOfPlayerOne));
        Assertions.assertTrue(territoryService
            .areConnected(WESTERN_UNITED_STATES.toEntity(), ICELAND.toEntity(),
                territoriesOfPlayerOne));
    }

    @Test
    void testNonConnectedTerritories() {
        Assertions.assertFalse(territoryService
            .areConnected(ALASKA.toEntity(), WESTERN_EUROPE.toEntity(), territoriesOfPlayerOne));
        Assertions.assertFalse(
            territoryService.areConnected(PERU.toEntity(), ICELAND.toEntity(),
                territoriesOfPlayerOne));
        Assertions.assertFalse(
            territoryService.areConnected(ARGENTINA.toEntity(), ICELAND.toEntity(),
                territoriesOfPlayerOne));
    }

    @Test
    void testConnectedWithSameTerritories() {
        Assertions.assertTrue(
            territoryService
                .areConnected(ALASKA.toEntity(), ALASKA.toEntity(), territoriesOfPlayerOne));
        Assertions.assertFalse(
            territoryService
                .areConnected(ARGENTINA.toEntity(), ARGENTINA.toEntity(), territoriesOfPlayerOne));
    }
}
