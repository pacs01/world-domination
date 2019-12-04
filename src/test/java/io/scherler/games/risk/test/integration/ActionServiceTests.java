package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.models.request.Deployment;
import io.scherler.games.risk.models.request.Game;
import io.scherler.games.risk.models.request.Movement;
import io.scherler.games.risk.models.request.Territory;
import io.scherler.games.risk.models.request.UserAccount;
import io.scherler.games.risk.models.response.AttackResult;
import io.scherler.games.risk.models.response.MovementInfo;
import io.scherler.games.risk.models.response.TerritoryInfo;
import io.scherler.games.risk.services.game.DiceService;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.OccupationService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.ActionService;
import io.scherler.games.risk.services.identity.UserAccountService;
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

    @Autowired
    private PlayerService playerService;

    @MockBean
    private DiceService mockedDiceService;

    @Autowired
    private UserAccountService userAccountService;

    private GameEntity game;
    private PlayerEntity firstPlayer;
    private PlayerEntity secondPlayer;

    @BeforeEach
    void init() {
        val creator = userAccountService.createNew(new UserAccount("testuser"));
        game = gameService.createNew(new Game("testgame", 2, "helloworld"), creator);
        firstPlayer = game.getActivePlayer();
        secondPlayer = playerService.getNextPlayer(game, firstPlayer.getId());

        actionService.occupy(new Territory("Egypt"), game.getId(), firstPlayer.getId());
        actionService.occupy(new Territory("Southern Europe"), game.getId(), firstPlayer.getId());
        actionService.occupy(new Territory("Congo"), game.getId(), firstPlayer.getId());
        actionService
            .deploy(new Deployment("Southern Europe", 5), game.getId(), firstPlayer.getId());
        gameService.endTurn(game.getId(), firstPlayer.getId());

        actionService.occupy(new Territory("Japan"), game.getId(), secondPlayer.getId());
        actionService.occupy(new Territory("Middle East"), game.getId(), secondPlayer.getId());
        gameService.endTurn(game.getId(), secondPlayer.getId());
    }

    @Test
    void testOccupation() {
        Assertions.assertEquals(game.getActivePlayer(), firstPlayer);

        val occupation = new Territory("Peru");
        val territoryInfo = actionService.occupy(occupation, game.getId(), firstPlayer.getId());

        Assertions.assertEquals(new TerritoryInfo("Peru", firstPlayer.getColor().toString(), 1),
            territoryInfo);

        val targetOccupation = occupationService
            .getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Peru");
        Assertions.assertTrue(
            occupationService.isOccupied(game.getId(), targetOccupation.getTerritory()));
        Assertions.assertTrue(targetOccupation.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetOccupation.getPlayer());
    }

    @Test
    void testDeployment() {
        Assertions.assertEquals(game.getActivePlayer(), firstPlayer);

        val deployment = new Deployment("Egypt", 5);
        val territoryInfo = actionService.deploy(deployment, game.getId(), firstPlayer.getId());

        Assertions.assertEquals(new TerritoryInfo("Egypt", firstPlayer.getColor().toString(), 6),
            territoryInfo);

        val targetTerritory = occupationService
            .getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Egypt");
        Assertions.assertTrue(targetTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetTerritory.getPlayer());
        Assertions.assertEquals(6, targetTerritory.getUnits());
    }

    @Test
    void testMovement() {
        Assertions.assertEquals(game.getActivePlayer(), firstPlayer);

        val movement = new Movement("Egypt", 5, "Southern Europe");
        val movementInfo = actionService.move(movement, game.getId(), firstPlayer.getId());

        Assertions.assertEquals(
            new MovementInfo(
                new TerritoryInfo("Southern Europe", firstPlayer.getColor().toString(), 1),
                new TerritoryInfo("Egypt", firstPlayer.getColor().toString(), 6)),
            movementInfo);

        val sourceTerritory = occupationService
            .getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Southern Europe");
        Assertions.assertTrue(sourceTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, sourceTerritory.getPlayer());
        Assertions.assertEquals(1, sourceTerritory.getUnits());

        val targetTerritory = occupationService
            .getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Egypt");
        Assertions.assertTrue(targetTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetTerritory.getPlayer());
        Assertions.assertEquals(6, targetTerritory.getUnits());
    }

    @Test
    void testNotConnectedMovement() {
        Assertions.assertEquals(game.getActivePlayer(), firstPlayer);

        val movement = new Movement("Congo", 5, "Southern Europe");

        Assertions.assertThrows(IllegalArgumentException.class,
            () -> actionService.move(movement, game.getId(), firstPlayer.getId()));
    }

    @Test
    void testAttackWin() {
        Assertions.assertEquals(game.getActivePlayer(), firstPlayer);

        val attackDices = Arrays.asList(2, 4, 6);
        val defendDices = Collections.singletonList(3);
        Mockito.when(mockedDiceService.rollDices(attackDices.size())).thenReturn(attackDices);
        Mockito.when(mockedDiceService.rollDices(defendDices.size())).thenReturn(defendDices);

        val movement = new Movement("Middle East", 5, "Southern Europe");

        val attackResult = actionService.attack(movement, game.getId(), firstPlayer.getId());

        Assertions.assertEquals(new AttackResult(
            new MovementInfo(
                new TerritoryInfo("Southern Europe", firstPlayer.getColor().toString(), 1),
                new TerritoryInfo("Middle East", firstPlayer.getColor().toString(), 5)),
            attackDices, defendDices), attackResult);

        val sourceTerritory = occupationService
            .getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Southern Europe");
        Assertions.assertTrue(sourceTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, sourceTerritory.getPlayer());
        Assertions.assertEquals(1, sourceTerritory.getUnits());

        val targetTerritory = occupationService
            .getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Middle East");
        Assertions.assertTrue(targetTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, targetTerritory.getPlayer());
        Assertions.assertEquals(5, targetTerritory.getUnits());
    }

    @Test
    void testAttackLost() {
        Assertions.assertEquals(game.getActivePlayer(), firstPlayer);

        val attackDices = Arrays.asList(2, 4, 3);
        val defendDices = Collections.singletonList(5);
        Mockito.when(mockedDiceService.rollDices(attackDices.size())).thenReturn(attackDices);
        Mockito.when(mockedDiceService.rollDices(defendDices.size())).thenReturn(defendDices);

        val movement = new Movement("Middle East", 5, "Southern Europe");

        val attackResult = actionService.attack(movement, game.getId(), firstPlayer.getId());

        Assertions.assertEquals(new AttackResult(
            new MovementInfo(
                new TerritoryInfo("Southern Europe", firstPlayer.getColor().toString(), 5),
                new TerritoryInfo("Middle East", secondPlayer.getColor().toString(), 1)),
            attackDices, defendDices), attackResult);

        val sourceTerritory = occupationService
            .getOccupationByPlayer(game.getId(), firstPlayer.getId(), "Southern Europe");
        Assertions.assertTrue(sourceTerritory.isOccupiedBy(firstPlayer));
        Assertions.assertEquals(firstPlayer, sourceTerritory.getPlayer());
        Assertions.assertEquals(5, sourceTerritory.getUnits());

        val targetTerritory = occupationService
            .getOccupationByPlayer(game.getId(), secondPlayer.getId(), "Middle East");
        Assertions.assertTrue(targetTerritory.isOccupiedBy(secondPlayer));
        Assertions.assertEquals(secondPlayer, targetTerritory.getPlayer());
        Assertions.assertEquals(1, targetTerritory.getUnits());
    }

    @Test
    void testNotConnectedAttack() {
        Assertions.assertEquals(game.getActivePlayer(), firstPlayer);

        val attackDices = Arrays.asList(2, 4, 6);
        val defendDices = Collections.singletonList(3);
        Mockito.when(mockedDiceService.rollDices(attackDices.size())).thenReturn(attackDices);
        Mockito.when(mockedDiceService.rollDices(defendDices.size())).thenReturn(defendDices);

        val movement = new Movement("Japan", 5, "Southern Europe");

        Assertions.assertThrows(IllegalArgumentException.class,
            () -> actionService.attack(movement, game.getId(), firstPlayer.getId()));
    }
}
