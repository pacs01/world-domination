package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.models.response.AttackResult;
import io.scherler.games.risk.models.request.Deployment;
import io.scherler.games.risk.models.request.Game;
import io.scherler.games.risk.models.request.Movement;
import io.scherler.games.risk.models.response.MovementInfo;
import io.scherler.games.risk.models.request.Occupation;
import io.scherler.games.risk.models.response.TerritoryInfo;
import io.scherler.games.risk.services.map.ActionService;
import io.scherler.games.risk.services.map.DiceService;
import io.scherler.games.risk.services.map.GameService;
import io.scherler.games.risk.services.map.OccupationService;
import java.util.Arrays;
import java.util.Collections;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ActionServiceTests {

    @Autowired
    private OccupationService occupationService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private GameService gameService;

    @MockBean
    private DiceService mockedDiceService;

    private GameEntity game;
    private PlayerEntity firstPlayer;

    @BeforeEach
    void init() {
        game = gameService.createNew(new Game("testgame", 4, "helloworld"));
        firstPlayer = game.getPlayerEntities().stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("No player entity found!"));
        val secondPlayer = game.getPlayerEntities()
                               .stream()
                               .filter(p -> !p.equals(firstPlayer))
                               .findFirst()
                               .orElseThrow(() -> new ResourceNotFoundException("No second player entity found!"));

        actionService.occupy(new Occupation("Egypt"), game.getId(), firstPlayer.getId());
        actionService.occupy(new Occupation("Southern Europe"), game.getId(), firstPlayer.getId());
        actionService.deploy(new Deployment("Southern Europe", 5), game.getId(), firstPlayer.getId());

        actionService.occupy(new Occupation("Japan"), game.getId(), secondPlayer.getId());
    }

    @Test
    void testOccupation() {
        val occupation = new Occupation("Peru");

        val territoryInfo = actionService.occupy(occupation, game.getId(), firstPlayer.getId());

        Assertions.assertEquals(new TerritoryInfo("Peru", 1), territoryInfo);

        val targetTerritory = occupationService.getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Peru");
        Assertions.assertTrue(targetTerritory.isOccupied());
        Assertions.assertTrue(targetTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetTerritory.getPlayer());
    }

    @Test
    void testDeployment() {
        val deployment = new Deployment("Egypt", 5);

        val territoryInfo = actionService.deploy(deployment, game.getId(), firstPlayer.getId());

        Assertions.assertEquals(new TerritoryInfo("Egypt", 6), territoryInfo);

        val targetTerritory = occupationService.getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Egypt");
        Assertions.assertTrue(targetTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetTerritory.getPlayer());
        Assertions.assertEquals(6, targetTerritory.getUnits());
    }

    @Test
    void testMovement() {
        val movement = new Movement("Egypt", 5, "Southern Europe");

        val movementInfo = actionService.move(movement, game.getId(), firstPlayer.getId());

        Assertions.assertEquals(new MovementInfo(new TerritoryInfo("Southern Europe", 1), new TerritoryInfo("Egypt", 6)), movementInfo);

        val sourceTerritory = occupationService.getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Southern Europe");
        Assertions.assertTrue(sourceTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, sourceTerritory.getPlayer());
        Assertions.assertEquals(1, sourceTerritory.getUnits());

        val targetTerritory = occupationService.getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Egypt");
        Assertions.assertTrue(targetTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetTerritory.getPlayer());
        Assertions.assertEquals(6, targetTerritory.getUnits());
    }

    @Test
    void testAttack() {
        val attackDices = Arrays.asList(2, 4, 6);
        val defendDices = Collections.singletonList(3);
        Mockito.when(mockedDiceService.rollDices(3)).thenReturn(attackDices);
        Mockito.when(mockedDiceService.rollDices(1)).thenReturn(defendDices);

        val movement = new Movement("Japan", 5, "Southern Europe");

        val attackResult = actionService.attack(movement, game.getId(), firstPlayer.getId());

        Assertions.assertEquals(new AttackResult(new MovementInfo(new TerritoryInfo("Southern Europe", 1), new TerritoryInfo("Japan", 5)), attackDices, defendDices), attackResult);

        val sourceTerritory = occupationService.getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Southern Europe");
        Assertions.assertTrue(sourceTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, sourceTerritory.getPlayer());
        Assertions.assertEquals(1, sourceTerritory.getUnits());

        val targetTerritory = occupationService.getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Japan");
        Assertions.assertTrue(targetTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetTerritory.getPlayer());
        Assertions.assertEquals(5, targetTerritory.getUnits());
    }
}
