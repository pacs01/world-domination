package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.models.GameState;
import io.scherler.games.risk.models.request.NewGame;
import io.scherler.games.risk.models.request.Territory;
import io.scherler.games.risk.models.request.UserAccount;
import io.scherler.games.risk.models.request.UserRequest;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.ActionService;
import io.scherler.games.risk.services.identity.UserAccountService;
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
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ActionService actionService;

    private GameEntity game;
    private PlayerEntity firstPlayer;
    private PlayerEntity secondPlayer;

    @BeforeEach
    void init() {
        val creator = userAccountService.createNew(new UserAccount("testuser"));
        val newGame = new NewGame("testgame", 4, "helloworld");
        game = gameService.create(new UserRequest<>(newGame, creator));
        firstPlayer = game.getActivePlayer();
        secondPlayer = playerService.getNextPlayer(game, firstPlayer.getId());
    }

    @Test
    void testCreateNewGame() {
        val creator = userAccountService.createNew(new UserAccount("testuser2"));
        val newGame = new NewGame("new-test-game", 4, "helloworld");
        val game = gameService.create(new UserRequest<>(newGame, creator));

        Assertions.assertEquals(4, game.getPlayers().size());
        Assertions.assertNotNull(game.getActivePlayer());
        Assertions.assertEquals(GameState.ACTIVE, game.getState());
    }

    @Test
    void testEndTurnWithOccupation() {
        val occupation = new Territory("Peru");
        actionService.occupy(occupation, game.getId(), firstPlayer.getId());

        val turnResult = actionService.endTurn(game.getId(), firstPlayer.getId());

        Assertions.assertEquals(secondPlayer, turnResult.getNextPlayer());
        Assertions.assertNotNull(turnResult.getCard());
        Assertions.assertEquals(secondPlayer, game.getActivePlayer());
    }

    @Test
    void testEndTurnWithoutOccupation() {
        val turnResult = actionService.endTurn(game.getId(), firstPlayer.getId());

        Assertions.assertEquals(secondPlayer, turnResult.getNextPlayer());
        Assertions.assertNull(turnResult.getCard());
        Assertions.assertEquals(secondPlayer, game.getActivePlayer());
    }
}
