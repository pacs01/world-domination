package io.scherler.games.risk.test.unit;

import io.scherler.games.risk.entities.game.OccupationEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.map.TerritoryRepository;
import io.scherler.games.risk.services.map.TerritoryService;
import io.scherler.games.risk.test.unit.testdata.Game;
import io.scherler.games.risk.test.unit.testdata.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.stream.Collectors;

class ConnectedTerritoryValidationTest {

    @Mock
    private TerritoryRepository territoryRepository;

    private TerritoryService territoryService = new TerritoryService(territoryRepository);

    private final World world = World.createWithIds(); // todo: use testdata world also for Database initialization at startup
    private final Game game = Game.createWithIds(world);

    private List<TerritoryEntity> territories;

    @BeforeEach
    void setUp() {
        territories = game.getOccupations().stream().filter(o -> o.getPlayer().getId().equals(game.getPlayerOne().getId())).map(OccupationEntity::getTerritory).collect(Collectors.toList());
    }

    @Test
    void testConnectedTerritories() {
        Assertions.assertTrue(territoryService.areConnected(world.getAlaska(), world.getEasternUnitedStates(), territories));
        Assertions.assertTrue(territoryService.areConnected(world.getWesternUnitedStates(), world.getIceland(), territories));
    }

    @Test
    void testNonConnectedTerritories() {
        Assertions.assertFalse(territoryService.areConnected(world.getAlaska(), world.getWesternEurope(), territories));
        Assertions.assertFalse(territoryService.areConnected(world.getPeru(), world.getIceland(), territories));
        Assertions.assertFalse(territoryService.areConnected(world.getArgentina(), world.getIceland(), territories));
    }

    @Test
    void testConnectedWithSameTerritories() {
        Assertions.assertTrue(territoryService.areConnected(world.getAlaska(), world.getAlaska(), territories));
        Assertions.assertFalse(territoryService.areConnected(world.getArgentina(), world.getArgentina(), territories));
    }
}
