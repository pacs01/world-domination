package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.entities.game.CardEntity;
import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.models.request.identity.UserRequest;
import io.scherler.games.risk.models.request.map.Territory;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.OccupationService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.ActionService;
import io.scherler.games.risk.services.map.TerritoryService;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class EntityDefinitionTest {

    @Autowired
    private OccupationService occupationService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TerritoryService territoryService;

    @Autowired
    private DatabaseTestHelpers databaseTestHelpers;

    @Test
    void testToString() {
        GameEntity game = databaseTestHelpers.generateGameWithMapCreator("testgame", 4);
        playerService.create(new UserRequest<>(game, game.getCreator()));
        databaseTestHelpers.generatePlayers(game, 3);
        gameService.startGame(game);
        actionService
            .occupy(new Territory("Southern Europe"), game.getId(), game.getActivePlayer().getId());
        game = gameService.get(game.getId());

        // game creator should be (active) player as well to check for circular dependencies below!
        Assertions.assertEquals(game.getCreator(), game.getActivePlayer().getUserAccount());

        Assertions.assertEquals(
            "GameEntity(name=testgame, state=ACTIVE, players=[PlayerEntity"
                + "(userAccount=UserAccountEntity(name=testgame-user-1), color=BLUE, position=2, "
                + "unitsToDeploy=10), PlayerEntity(userAccount=UserAccountEntity"
                + "(name=testgame-user-0), color=GREEN, position=1, unitsToDeploy=10), "
                + "PlayerEntity(userAccount=UserAccountEntity(name=testgame-user-2), "
                + "color=YELLOW, position=3, unitsToDeploy=10), PlayerEntity"
                + "(userAccount=UserAccountEntity(name=testadmin), color=RED, position=0, "
                + "unitsToDeploy=10)], occupations=[], cards=[], round=1, "
                + "activePlayer=PlayerEntity(userAccount=UserAccountEntity(name=testadmin), "
                + "color=RED, position=0, unitsToDeploy=10))",
            game.toString());

        Assertions.assertEquals("UserAccountEntity(name=testadmin)", game.getCreator().toString());

        Assertions
            .assertEquals("MapEntity(name=helloworld, creator=UserAccountEntity(name=testadmin))",
                game.getMap().toString());

        Assertions.assertEquals(
            "ContinentEntity(name=North America, map=MapEntity(name=helloworld, "
                + "creator=UserAccountEntity(name=testadmin)))",
            game.getMap().getContinents().iterator().next().toString());

        Assertions.assertEquals(
            "TerritoryEntity(name=Southern Europe, continent=ContinentEntity(name=Europe, "
                + "map=MapEntity(name=helloworld, creator=UserAccountEntity(name=testadmin))))",
            territoryService.getTerritory(game.getMap().getId(), "Southern Europe").toString());

        Assertions.assertEquals(
            "PlayerEntity(userAccount=UserAccountEntity(name=testadmin), color=RED, position=0, "
                + "unitsToDeploy=10)",
            game.getActivePlayer().toString());

        // todo fix this bug (game.occupations always empty?)
//        Assertions.assertEquals("OccupationEntity(units=1, occupiedInRound=1)",
//            game.getActivePlayer().getOccupations().iterator().next().toString());
        Assertions.assertEquals("OccupationEntity(units=1, occupiedInRound=1)", occupationService
            .getOccupationByPlayer(game.getId(), game.getActivePlayer().getId(), "Southern Europe")
            .toString());

        val card = new CardEntity(game,
            territoryService.getTerritory(game.getMap().getId(), "Southern Europe"),
            game.getActivePlayer());
        Assertions.assertEquals(
            "CardEntity(territory=TerritoryEntity(name=Southern Europe, continent=ContinentEntity"
                + "(name=Europe, map=MapEntity(name=helloworld, creator=UserAccountEntity"
                + "(name=testadmin)))), player=PlayerEntity(userAccount=UserAccountEntity"
                + "(name=testadmin), color=RED, position=0, unitsToDeploy=10))",
            card.toString());
    }

}
