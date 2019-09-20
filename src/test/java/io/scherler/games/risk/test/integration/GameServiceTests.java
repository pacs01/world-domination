package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.controllers.ResourceNotFoundException;
import io.scherler.games.risk.entities.GameEntity;
import io.scherler.games.risk.entities.GameRepository;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.models.Game;
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

    @Autowired
    private GameRepository gameRepository;

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
    }

    @Test
    void testEndTurn() {
        val nextPlayer = gameService.endTurn(game.getId(), firstPlayer.getId());

        Assertions.assertEquals(secondPlayer, nextPlayer);
        Assertions.assertEquals(secondPlayer, game.getActivePlayer());
    }
}
