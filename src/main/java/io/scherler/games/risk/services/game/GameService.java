package io.scherler.games.risk.services.game;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.repositories.game.GameRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.GameState;
import io.scherler.games.risk.models.request.NewGame;
import io.scherler.games.risk.models.request.UserRequest;
import io.scherler.games.risk.services.map.MapService;
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
        newGameEntity
            .addPlayers(playerService.generatePlayers(newGameEntity, newGame.getNumberOfPlayers()));
        newGameEntity.setActivePlayer(getFirstPlayer(newGameEntity));

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
        return entityRepository.save(game);
    }

    public GameEntity endGame(GameEntity game, GameState endState) {
        game.setState(endState);
        return entityRepository.save(game);
    }
}
