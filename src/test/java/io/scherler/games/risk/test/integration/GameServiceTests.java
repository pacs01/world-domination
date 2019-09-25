package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.entities.GameEntity;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.models.GameState;
import io.scherler.games.risk.models.request.Game;
import io.scherler.games.risk.services.GameService;
import java.util.Comparator;
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

    private GameEntity game;
    private PlayerEntity firstPlayer;
    private PlayerEntity secondPlayer;

    @BeforeEach
    void init() {
        game = gameService.createNew(new Game("testgame", 4));
        firstPlayer = game.getPlayerEntities()
                          .stream()
                          .min(Comparator.comparing(PlayerEntity::getPosition))
                          .orElseThrow(() -> new ResourceNotFoundException("No first player entity found!"));
        secondPlayer = game.getPlayerEntities()
                           .stream()
                           .filter(p -> !p.equals(firstPlayer))
                           .min(Comparator.comparing(PlayerEntity::getPosition))
                           .orElseThrow(() -> new ResourceNotFoundException("No second player entity found!"));
    }

    @Test
    void testCreateNewGame() {
        val game = gameService.createNew(new Game("new-test-game", 4));

        Assertions.assertEquals(4, game.getPlayerEntities().size());
        Assertions.assertNotNull(game.getActivePlayer());
        Assertions.assertEquals(GameState.ACTIVE, game.getState());
    }

    @Test
    void testEndTurn() {
        val turnResult = gameService.endTurn(game.getId(), firstPlayer.getId());

        Assertions.assertEquals(secondPlayer, turnResult.getNextPlayer());
        Assertions.assertNull(turnResult.getCard());
        Assertions.assertEquals(secondPlayer, game.getActivePlayer());
    }
}
