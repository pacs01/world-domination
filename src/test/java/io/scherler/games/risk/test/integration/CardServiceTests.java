package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.entities.GameEntity;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.request.Game;
import io.scherler.games.risk.services.CardService;
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
class CardServiceTests {

    @Autowired
    private CardService cardService;

    @Autowired
    private GameService gameService;

    private GameEntity game;
    private PlayerEntity firstPlayer;

    @BeforeEach
    void init() {
        game = gameService.createNew(new Game("testgame", 4, "helloworld"));
        firstPlayer = game.getPlayerEntities()
                          .stream()
                          .min(Comparator.comparing(PlayerEntity::getPosition))
                          .orElseThrow(() -> new ResourceNotFoundException("No first player entity found!"));
    }

    @Test
    void testDrawCard() {
        val numberOfCards = cardService.getAllCards(game.getId()).size();
        val card = cardService.drawNextCard(game, firstPlayer);

        Assertions.assertEquals(numberOfCards + 1, cardService.getAllCards(game.getId()).size());
        Assertions.assertTrue(cardService.getCardsByPlayer(game.getId(), firstPlayer.getId()).stream().anyMatch(c -> c.getTerritory().getName().equals(card.getTerritory())));
    }
}
