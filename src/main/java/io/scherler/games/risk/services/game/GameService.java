package io.scherler.games.risk.services.game;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.repositories.game.GameRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.GameState;
import io.scherler.games.risk.models.request.Game;
import io.scherler.games.risk.services.map.MapService;
import java.util.Comparator;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final MapService mapService;

    public GameService(GameRepository gameRepository, PlayerService playerService,
        MapService mapService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.mapService = mapService;
    }

    @Transactional
    public GameEntity createNew(Game newGame, UserAccountEntity creator) {
        val map = mapService.getMap(newGame.getMap());
        val newGameEntity = new GameEntity(newGame.getName(), creator, map);
        newGameEntity
            .addPlayers(playerService.generatePlayers(newGameEntity, newGame.getNumberOfPlayers()));
        newGameEntity.setActivePlayer(getFirstPlayer(newGameEntity));

        return gameRepository.save(newGameEntity);
    }

    private PlayerEntity getFirstPlayer(GameEntity gameEntity) {
        return gameEntity.getPlayers()
            .stream()
            .min(Comparator.comparing(PlayerEntity::getPosition))
            .orElseThrow(
                () -> new ResourceNotFoundException("No resource of type 'Player' found!"));
    }

    public GameEntity getGame(long gameId) {
        return gameRepository.findById(gameId)
            .orElseThrow(() -> new ResourceNotFoundException("Game", gameId));
    }

    public GameEntity endTurn(GameEntity game) {
        val nextPlayer = playerService.getNextPlayer(game, game.getActivePlayer().getId());
        game.setActivePlayer(nextPlayer);
        return gameRepository.save(game);
    }

    public GameEntity endGame(GameEntity game, GameState endState) {
        game.setState(endState);
        return gameRepository.save(game);
    }
}
