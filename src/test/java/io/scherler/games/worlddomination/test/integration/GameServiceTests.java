package io.scherler.games.worlddomination.test.integration;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.game.PlayerEntity;
import io.scherler.games.worlddomination.models.GameState;
import io.scherler.games.worlddomination.models.request.map.Territory;
import io.scherler.games.worlddomination.services.game.PlayerService;
import io.scherler.games.worlddomination.services.game.action.ActionService;
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
class GameServiceTests {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private DatabaseTestHelpers databaseTestHelpers;

    private GameEntity game;
    private PlayerEntity firstPlayer;
    private PlayerEntity secondPlayer;

    @BeforeEach
    void init() {
        game = databaseTestHelpers.generateActiveGame("testgame", 4, "testuser");
        firstPlayer = game.getActivePlayer();
        secondPlayer = playerService.getNextPlayer(game, firstPlayer.getId());
    }

    @Test
    void testCreateNewGame() {
        val game = databaseTestHelpers.generateGame("new-test-game", 4, "testuser2");

        Assertions.assertEquals(0, game.getPlayers().size());
        Assertions.assertNull(game.getActivePlayer());
        Assertions.assertEquals(GameState.INITIALISATION, game.getState());
        Assertions.assertEquals(0, game.getRound());
    }

    @Test
    void testCreateNewGameWithPlayers() {
        val game = databaseTestHelpers.generateActiveGame("new-test-game", 4, "testuser2");

        Assertions.assertEquals(4, game.getPlayers().size());
        Assertions.assertNotNull(game.getActivePlayer());
        Assertions.assertEquals(GameState.ACTIVE, game.getState());
        Assertions.assertEquals(1, game.getRound());
    }

    @Test
    void testEndTurnWithOccupation() {
        val occupation = new Territory("Peru");
        actionService.occupy(occupation, game.getId(), firstPlayer.getId());

        val turnResult = actionService.endTurn(game.getId(), firstPlayer.getId());

        Assertions.assertEquals(secondPlayer, turnResult.getNextPlayer());
        Assertions.assertNotNull(turnResult.getCard());
        Assertions.assertEquals(secondPlayer, game.getActivePlayer());
        Assertions.assertEquals(2, game.getRound());
    }

    @Test
    void testEndTurnWithoutOccupation() {
        val turnResult = actionService.endTurn(game.getId(), firstPlayer.getId());

        Assertions.assertEquals(secondPlayer, turnResult.getNextPlayer());
        Assertions.assertNull(turnResult.getCard());
        Assertions.assertEquals(secondPlayer, game.getActivePlayer());
        Assertions.assertEquals(2, game.getRound());
    }
}
