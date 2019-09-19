package io.scherler.games.risk.services;

import io.scherler.games.risk.controllers.ResourceNotFoundException;
import io.scherler.games.risk.entities.GameEntity;
import io.scherler.games.risk.entities.GameRepository;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.models.Game;
import java.util.Comparator;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;

    public GameService(GameRepository gameRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    @Transactional
    public GameEntity createNew(Game newGame) {
        val newGameEntity = new GameEntity(newGame.getName());
        newGameEntity.addPlayers(playerService.generatePlayers(newGameEntity, newGame.getNumberOfPlayers()));
        newGameEntity.setActivePlayer(getFirstPlayer(newGameEntity));

        return gameRepository.save(newGameEntity);
    }

    private PlayerEntity getFirstPlayer(GameEntity gameEntity) {
        return gameEntity.getPlayerEntities()
                         .stream()
                         .min(Comparator.comparing(PlayerEntity::getPosition))
                         .orElseThrow(() -> new ResourceNotFoundException("No resource of type 'Player' found!"));
    }

    @Transactional
    public PlayerEntity endTurn(long gameId, long playerId) {
        val game = getGame(gameId);
        val nextPlayer = playerService.getNextPlayer(game, playerId);
        game.setActivePlayer(nextPlayer);
        gameRepository.save(game);
        return nextPlayer;
    }

    public GameEntity getGame(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new ResourceNotFoundException("Game", gameId));
    }
}
