package io.scherler.games.worlddomination.test.unit;

import io.scherler.games.worlddomination.entities.repositories.map.TerritoryRepository;
import io.scherler.games.worlddomination.services.map.TerritoryService;
import io.scherler.games.worlddomination.test.unit.testdata.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class AdjacentTerritoryValidationTest {

    @Mock
    private TerritoryRepository territoryRepository;

    private TerritoryService territoryService = new TerritoryService(territoryRepository);

    private final World world = World.createWithIds();

    @Test
    void testAdjacentTerritories() {
        Assertions.assertTrue(territoryService.areAdjacent(world.getAlaska(), world.getAlberta()));
        Assertions.assertTrue(
            territoryService.areAdjacent(world.getNorthwestTerritory(), world.getOntario()));
    }

    @Test
    void testNonadjacentTerritories() {
        Assertions.assertFalse(territoryService.areAdjacent(world.getAlaska(), world.getOntario()));
        Assertions
            .assertFalse(territoryService.areAdjacent(world.getAlaska(), world.getGreenland()));
    }

    @Test
    void testAdjacentWithSameTerritories() {
        Assertions.assertFalse(territoryService.areAdjacent(world.getAlaska(), world.getAlaska()));
        Assertions
            .assertFalse(territoryService.areAdjacent(world.getGreenland(), world.getGreenland()));
    }
}
