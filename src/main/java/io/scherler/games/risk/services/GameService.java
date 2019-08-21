package io.scherler.games.risk.services;

import io.scherler.games.risk.entities.GameEntity;
import io.scherler.games.risk.entities.GameRepository;
import io.scherler.games.risk.models.Game;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;

    public GameService(GameRepository gameRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    public GameEntity createNew(Game newGame) {
        val newGameEntity = new GameEntity(newGame.getName());
        newGameEntity.addPlayers(playerService.generatePlayers(newGameEntity, newGame.getNumberOfPlayers()));

        return gameRepository.save(newGameEntity);
    }
}
