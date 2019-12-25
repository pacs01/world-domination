package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.entities.game.CardEntity;
import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.models.request.identity.UserRequest;
import io.scherler.games.risk.models.request.map.Territory;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.ActionService;
import io.scherler.games.risk.services.map.ContinentService;
import io.scherler.games.risk.services.map.TerritoryService;
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
class EntityDefinitionTest {

    private static final String SOUTH_AMERICA = "South America";
    private static final String EUROPE = "Europe";
    private static final String SOUTHERN_EUROPE = "Southern Europe";

    @Autowired
    private ActionService actionService;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ContinentService continentService;

    @Autowired
    private TerritoryService territoryService;

    @Autowired
    private DatabaseTestHelpers databaseTestHelpers;

    private GameEntity game;
    private CardEntity card;

    @BeforeEach
    void init() {
        game = databaseTestHelpers.generateGameWithMapCreator("testgame", 4);
        playerService.create(new UserRequest<>(game, game.getCreator()));
        databaseTestHelpers.generatePlayers(game, 3);
        gameService.startGame(game);
        actionService
            .occupy(new Territory(SOUTHERN_EUROPE), game.getId(), game.getActivePlayer().getId());
        card = new CardEntity(game,
            territoryService.getByName(game.getMap(), SOUTHERN_EUROPE),
            game.getActivePlayer());
    }

    @Test
    void testGameCreation() {
        Assertions.assertNotNull(game.getCreator());
        Assertions.assertEquals(game.getCreator(), game.getActivePlayer().getUserAccount());
        Assertions.assertNotNull(game.getMap());
        Assertions.assertNotNull(game.getActivePlayer());
        Assertions.assertEquals(4, game.getPlayers().size());
        Assertions.assertEquals(1, game.getOccupations().size());
        Assertions.assertEquals(1, game.getCards().size());
    }

    @Test
    void testCardCreation() {
        val card = game.getCards().iterator().next();
        Assertions.assertNotNull(card.getGame());
        Assertions.assertEquals(game, card.getGame());
        Assertions.assertNotNull(card.getTerritory());
        Assertions.assertEquals(territoryService.getByName(game.getMap(), SOUTHERN_EUROPE),
            card.getTerritory());
        Assertions.assertNotNull(card.getPlayer());
        Assertions.assertEquals(game.getActivePlayer(), card.getPlayer());
    }

    @Test
    void testOccupationCreation() {
        val occupation = game.getActivePlayer().getOccupations().iterator().next();
        Assertions.assertNotNull(occupation.getGame());
        Assertions.assertEquals(game, occupation.getGame());
        Assertions.assertNotNull(occupation.getTerritory());
        Assertions.assertEquals(territoryService.getByName(game.getMap(), SOUTHERN_EUROPE),
            occupation.getTerritory());
        Assertions.assertNotNull(occupation.getPlayer());
        Assertions.assertEquals(game.getActivePlayer(), occupation.getPlayer());
    }

    @Test
    void testPlayerCreation() {
        val player = game.getActivePlayer();
        Assertions.assertNotNull(player.getGame());
        Assertions.assertEquals(game, player.getGame());
        Assertions.assertNotNull(player.getUserAccount());
        Assertions.assertEquals(game.getCreator(), player.getUserAccount());
        Assertions.assertEquals(1, player.getOccupations().size());
        Assertions.assertEquals(1, player.getCards().size());
    }

    @Test
    void testUserAccountCreation() {
        val userAccount = game.getCreator();
        Assertions.assertEquals(1, userAccount.getGames().size());
        Assertions.assertEquals(1, userAccount.getMaps().size());
        Assertions.assertEquals(1, userAccount.getPlayers().size());
    }

    @Test
    void testMapCreation() {
        val map = game.getMap();
        Assertions.assertNotNull(map.getCreator());
        Assertions.assertEquals(game.getCreator(), map.getCreator());
        Assertions.assertEquals(1, map.getGames().size());
        Assertions.assertEquals(6, map.getContinents().size());
    }

    @Test
    void testContinentCreation() {
        val continent = continentService.getByName(game.getMap().getId(), SOUTH_AMERICA);
        Assertions.assertNotNull(continent.getMap());
        Assertions.assertEquals(game.getMap(), continent.getMap());
        Assertions.assertEquals(4, continent.getTerritories().size());
    }

    @Test
    void testTerritoryCreation() {
        val territory = territoryService.getByName(game.getMap(), SOUTHERN_EUROPE);
        Assertions.assertNotNull(territory.getContinent());
        Assertions.assertEquals(continentService.getByName(game.getMap().getId(), EUROPE),
            territory.getContinent());
        Assertions.assertEquals(6, territory.getAdjacentTerritories().size());
        Assertions.assertEquals(1, territory.getOccupations().size());
        Assertions.assertEquals(1, territory.getCards().size());
    }

    @Test
    void testToString() {
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
                + "unitsToDeploy=10)], occupations=[OccupationEntity(units=1, occupiedInRound=1)"
                + "], cards=[CardEntity(territory=TerritoryEntity(name=Southern Europe, "
                + "map=MapEntity(name=helloworld, creator=UserAccountEntity(name=testadmin)), "
                + "continent=ContinentEntity(name=Europe, map=MapEntity(name=helloworld, "
                + "creator=UserAccountEntity(name=testadmin)))), player=PlayerEntity"
                + "(userAccount=UserAccountEntity(name=testadmin), color=RED, position=0, "
                + "unitsToDeploy=10))], round=1, activePlayer=PlayerEntity"
                + "(userAccount=UserAccountEntity(name=testadmin), color=RED, position=0, "
                + "unitsToDeploy=10))",
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
            "TerritoryEntity(name=Southern Europe, map=MapEntity(name=helloworld, "
                + "creator=UserAccountEntity(name=testadmin)), continent=ContinentEntity"
                + "(name=Europe, map=MapEntity(name=helloworld, creator=UserAccountEntity"
                + "(name=testadmin))))",
            territoryService.getByName(game.getMap(), SOUTHERN_EUROPE).toString());

        Assertions.assertEquals(
            "PlayerEntity(userAccount=UserAccountEntity(name=testadmin), color=RED, "
                + "position=0, "
                + "unitsToDeploy=10)",
            game.getActivePlayer().toString());

        Assertions.assertEquals("OccupationEntity(units=1, occupiedInRound=1)",
            game.getActivePlayer().getOccupations().iterator().next().toString());

        Assertions.assertEquals(
            "CardEntity(territory=TerritoryEntity(name=Southern Europe, map=MapEntity"
                + "(name=helloworld, creator=UserAccountEntity(name=testadmin)), "
                + "continent=ContinentEntity(name=Europe, map=MapEntity(name=helloworld, "
                + "creator=UserAccountEntity(name=testadmin)))), player=PlayerEntity"
                + "(userAccount=UserAccountEntity(name=testadmin), color=RED, position=0, "
                + "unitsToDeploy=10))",
            card.toString());
    }
}
