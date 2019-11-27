package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.models.GameState;
import io.scherler.games.risk.models.request.Game;
import io.scherler.games.risk.models.request.UserAccount;
import io.scherler.games.risk.services.game.GameService;
import java.util.Comparator;

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
    private UserAccountService userAccountService;

    private GameEntity game;
    private PlayerEntity firstPlayer;
    private PlayerEntity secondPlayer;

    @BeforeEach
    void init() {
        val creator = userAccountService.createNew(new UserAccount("testuser"));
        game = gameService.createNew(new Game("testgame", 4, "helloworld"), creator);
        firstPlayer = game.getPlayers()
                          .stream()
                          .min(Comparator.comparing(PlayerEntity::getPosition))
                          .orElseThrow(() -> new ResourceNotFoundException("No first player entity found!"));
        secondPlayer = game.getPlayers()
                           .stream()
                           .filter(p -> !p.equals(firstPlayer))
                           .min(Comparator.comparing(PlayerEntity::getPosition))
                           .orElseThrow(() -> new ResourceNotFoundException("No second player entity found!"));
    }

    @Test
    void testCreateNewGame() {
        val creator = userAccountService.createNew(new UserAccount("testuser2"));
        val game = gameService.createNew(new Game("new-test-game", 4, "helloworld"), creator);

        Assertions.assertEquals(4, game.getPlayers().size());
        Assertions.assertNotNull(game.getActivePlayer());
        Assertions.assertEquals(GameState.ACTIVE, game.getState());
    }

    @Test
    void testEndTurn() {
        val turnResult = gameService.endTurn(game.getId(), firstPlayer.getId());

        Assertions.assertEquals(secondPlayer, turnResult.getNextPlayer());
        Assertions.assertNotNull(turnResult.getCard());
        Assertions.assertEquals(secondPlayer, game.getActivePlayer());
    }
}
