package io.scherler.games.worlddomination.test.unit;

import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.ALASKA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.ALBERTA;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.GREENLAND;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.NORTHWEST_TERRITORY;
import static io.scherler.games.worlddomination.test.unit.testdata.World.Territories.ONTARIO;

import io.scherler.games.worlddomination.entities.repositories.map.TerritoryRepository;
import io.scherler.games.worlddomination.services.map.TerritoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class AdjacentTerritoryValidationTest {

    @Mock
    private TerritoryRepository territoryRepository;

    private TerritoryService territoryService = new TerritoryService(territoryRepository);

    @Test
    void testAdjacentTerritories() {
        Assertions.assertTrue(territoryService.areAdjacent(ALASKA.toEntity(), ALBERTA.toEntity()));
        Assertions.assertTrue(
            territoryService.areAdjacent(NORTHWEST_TERRITORY.toEntity(), ONTARIO.toEntity()));
    }

    @Test
    void testNonadjacentTerritories() {
        Assertions.assertFalse(territoryService.areAdjacent(ALASKA.toEntity(), ONTARIO.toEntity()));
        Assertions
            .assertFalse(territoryService.areAdjacent(ALASKA.toEntity(), GREENLAND.toEntity()));
    }

    @Test
    void testAdjacentWithSameTerritories() {
        Assertions.assertFalse(territoryService.areAdjacent(ALASKA.toEntity(), ALASKA.toEntity()));
        Assertions
            .assertFalse(territoryService.areAdjacent(GREENLAND.toEntity(), GREENLAND.toEntity()));
    }
}
