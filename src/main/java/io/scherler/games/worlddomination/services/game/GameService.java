package io.scherler.games.worlddomination.services.game;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.game.PlayerEntity;
import io.scherler.games.worlddomination.entities.repositories.game.GameRepository;
import io.scherler.games.worlddomination.exceptions.ResourceNotFoundException;
import io.scherler.games.worlddomination.models.GameState;
import io.scherler.games.worlddomination.models.request.game.NewGame;
import io.scherler.games.worlddomination.models.request.identity.UserRequest;
import io.scherler.games.worlddomination.services.CrudService;
import io.scherler.games.worlddomination.services.map.MapService;
import java.util.Comparator;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class GameService extends CrudService<GameEntity, UserRequest<NewGame>> {

    private final PlayerService playerService;
    private final MapService mapService;

    public GameService(GameRepository gameRepository, PlayerService playerService,
        MapService mapService) {
        super(gameRepository);
        this.playerService = playerService;
        this.mapService = mapService;
    }

    @Override
    protected String getResourceName() {
        return "Game";
    }

    @Override
    public GameEntity create(UserRequest<NewGame> request) {
        val newGame = request.getRequestObject();
        val creator = request.getUserAccount();
        val map = mapService.getMap(newGame.getMap());
        val newGameEntity = new GameEntity(newGame.getName(), creator, map);

        return entityRepository.save(newGameEntity);
    }

    private PlayerEntity getFirstPlayer(GameEntity gameEntity) {
        return gameEntity.getPlayers()
            .stream()
            .min(Comparator.comparing(PlayerEntity::getPosition))
            .orElseThrow(
                () -> new ResourceNotFoundException("No resource of type 'Player' found!"));
    }

    public GameEntity endTurn(GameEntity game) {
        val nextPlayer = playerService.getNextPlayer(game, game.getActivePlayer().getId());
        game.setActivePlayer(nextPlayer);
        game.increaseRound();
        return entityRepository.save(game);
    }

    public GameEntity startGame(GameEntity game) {
        if (game.getPlayers().size() < 2) {
            throw new IllegalStateException("The game needs at least two players to be started");
        } else if (game.hasStarted()) {
            throw new IllegalStateException("The game has been started already");
        }

        game.setActivePlayer(getFirstPlayer(game));
        game.setState(GameState.ACTIVE);
        game.increaseRound();
        return entityRepository.save(game);
    }

    public GameEntity endGame(GameEntity game, GameState endState) {
        game.setState(endState);
        return entityRepository.save(game);
    }
}
