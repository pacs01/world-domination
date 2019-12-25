package io.scherler.games.worlddomination.test.integration;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.game.PlayerEntity;
import io.scherler.games.worlddomination.services.game.CardService;
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
    private DatabaseTestHelpers databaseTestHelpers;

    private GameEntity game;
    private PlayerEntity firstPlayer;

    @BeforeEach
    void init() {
        game = databaseTestHelpers.generateActiveGame("testgame", 4, "testuser");
        firstPlayer = game.getActivePlayer();
    }

    @Test
    void testDrawCard() {
        val numberOfCards = cardService.getAllCards(game.getId()).size();
        val card = cardService.drawNextCard(game, firstPlayer);

        Assertions.assertEquals(numberOfCards + 1, cardService.getAllCards(game.getId()).size());
        Assertions.assertTrue(
            cardService.getCardsByPlayer(game.getId(), firstPlayer.getId()).stream()
                .anyMatch(c -> c.getTerritory().getName().equals(card.getTerritory())));
    }
}
