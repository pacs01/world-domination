package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.models.Game;
import io.scherler.games.risk.models.Occupation;
import io.scherler.games.risk.services.ActionService;
import io.scherler.games.risk.services.GameService;
import io.scherler.games.risk.services.PlayerService;
import io.scherler.games.risk.services.TerritoryService;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ActionServiceTests {

    @Autowired
    private TerritoryService territoryService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private GameService gameService;

    @BeforeEach
    void init() {
        val game = new Game();
        game.setName("testgame");
        game.setNumberOfPlayers(4);
        gameService.createNew(game);

        val occupation = new Occupation();
        occupation.setTarget("Egypt");
        actionService.occupy(occupation, 1L);

        val otherOccupation = new Occupation();
        otherOccupation.setTarget("Southern Europe");
        actionService.occupy(otherOccupation, 2L);
    }

    @Test
    void testOccupation() {
        val occupation = new Occupation();
        occupation.setTarget("Peru");

        actionService.occupy(occupation, 1L);

        val occupiedTerritory = territoryService.getTerritory("Peru");
        Assertions.assertTrue(occupiedTerritory.isOccupied());
        Assertions.assertTrue(occupiedTerritory.isOccupiedBy(playerService.getPlayer(1L)));
    }

}
