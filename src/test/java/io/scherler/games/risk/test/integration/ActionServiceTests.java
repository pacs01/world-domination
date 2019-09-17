package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.controllers.ResourceNotFoundException;
import io.scherler.games.risk.entities.GameEntity;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.models.Deployment;
import io.scherler.games.risk.models.Game;
import io.scherler.games.risk.models.Movement;
import io.scherler.games.risk.models.MovementInfo;
import io.scherler.games.risk.models.Occupation;
import io.scherler.games.risk.models.TerritoryInfo;
import io.scherler.games.risk.services.ActionService;
import io.scherler.games.risk.services.GameService;
import io.scherler.games.risk.services.TerritoryService;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ActionServiceTests {

    @Autowired
    private TerritoryService territoryService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private GameService gameService;

    private GameEntity game;
    private PlayerEntity firstPlayer;
    private PlayerEntity secondPlayer;

    @BeforeEach
    void init() {
        this.game = gameService.createNew(new Game("testgame", 4));
        this.firstPlayer = game.getPlayerEntities().stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("No player entity found!"));
        this.secondPlayer = game.getPlayerEntities()
                                .stream()
                                .filter(p -> !p.equals(firstPlayer))
                                .findFirst()
                                .orElseThrow(() -> new ResourceNotFoundException("No second player entity found!"));

        actionService.occupy(new Occupation("Egypt"), firstPlayer.getId());
        actionService.occupy(new Occupation("Southern Europe"), firstPlayer.getId());
        actionService.deploy(new Deployment("Southern Europe", 5), firstPlayer.getId());
    }

    @Test
    void testOccupation() {
        val occupation = new Occupation("Peru");

        val territoryInfo = actionService.occupy(occupation, firstPlayer.getId());

        Assertions.assertEquals(new TerritoryInfo("Peru", 1), territoryInfo);

        val targetTerritory = territoryService.getTerritory("Peru");
        Assertions.assertTrue(targetTerritory.isOccupied());
        Assertions.assertTrue(targetTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetTerritory.getPlayer());
    }

    @Test
    void testDeployment() {
        val deployment = new Deployment("Egypt", 5);

        val territoryInfo = actionService.deploy(deployment, firstPlayer.getId());

        Assertions.assertEquals(new TerritoryInfo("Egypt", 6), territoryInfo);

        val targetTerritory = territoryService.getTerritory("Egypt");
        Assertions.assertTrue(targetTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetTerritory.getPlayer());
        Assertions.assertEquals(6, targetTerritory.getUnits());
    }

    @Test
    void testMovement() {
        val movement = new Movement("Egypt", 5, "Southern Europe");

        val movementInfo = actionService.move(movement, firstPlayer.getId());

        Assertions.assertEquals(new MovementInfo(new TerritoryInfo("Southern Europe", 1), new TerritoryInfo("Egypt", 6)), movementInfo);

        val sourceTerritory = territoryService.getTerritory("Southern Europe");
        Assertions.assertTrue(sourceTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, sourceTerritory.getPlayer());
        Assertions.assertEquals(1, sourceTerritory.getUnits());
        val targetTerritory = territoryService.getTerritory("Egypt");
        Assertions.assertTrue(targetTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetTerritory.getPlayer());
        Assertions.assertEquals(6, targetTerritory.getUnits());
    }
}
