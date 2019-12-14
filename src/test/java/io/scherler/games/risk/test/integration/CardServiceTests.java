package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.models.request.NewGame;
import io.scherler.games.risk.models.request.UserAccount;
import io.scherler.games.risk.models.request.UserRequest;
import io.scherler.games.risk.services.game.CardService;
import io.scherler.games.risk.services.game.GameService;
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
class CardServiceTests {

    @Autowired
    private CardService cardService;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private DatabaseTestHelpers databaseTestHelpers;

    private GameEntity game;
    private PlayerEntity firstPlayer;

    @BeforeEach
    void init() {
        val creator = userAccountService.create(new UserAccount("testuser"));
        val newGame = new NewGame("testgame", 4, "helloworld");
        game = gameService.create(new UserRequest<>(newGame, creator));
        databaseTestHelpers.generatePlayers(game, 4);
        gameService.startGame(game);
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
