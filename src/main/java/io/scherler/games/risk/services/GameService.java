package io.scherler.games.risk.services;

import io.scherler.games.risk.entities.GameEntity;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.entities.repositories.GameRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.request.Game;
import io.scherler.games.risk.models.response.Card;
import io.scherler.games.risk.models.response.TurnResult;
import java.util.Comparator;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final MapService mapService;

    public GameService(GameRepository gameRepository, PlayerService playerService, MapService mapService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.mapService = mapService;
    }

    @Transactional
    public GameEntity createNew(Game newGame) {
        val map = mapService.getMap(newGame.getMap());
        val newGameEntity = new GameEntity(newGame.getName(), map);
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
    public TurnResult endTurn(long gameId, long playerId) {
        val game = getGame(gameId);
        val card = drawCardIfAllowedTo(gameId, playerId);
        val nextPlayer = playerService.getNextPlayer(game, playerId);
        game.setActivePlayer(nextPlayer);
        gameRepository.save(game);
        return new TurnResult(nextPlayer, card);
    }

    @Transactional
    public Card drawCardIfAllowedTo(long gameId, long playerId) {
        if (false) { //todo: is allowed to draw a card? (has conquered at least one territory)
            return new Card("test");
        } else {
            return null;
        }
    }

    public GameEntity getGame(long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new ResourceNotFoundException("Game", gameId));
    }
}
