package io.scherler.games.risk.services.game;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.repositories.game.GameRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.GameState;
import io.scherler.games.risk.models.request.Game;
import io.scherler.games.risk.models.response.Card;
import io.scherler.games.risk.models.response.TurnResult;
import io.scherler.games.risk.services.map.MapService;
import java.util.Comparator;
import java.util.Optional;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final MapService mapService;
    private final CardService cardService;
    private final OccupationService occupationService;

    public GameService(GameRepository gameRepository, PlayerService playerService,
        MapService mapService, CardService cardService,
        OccupationService occupationService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.mapService = mapService;
        this.cardService = cardService;
        this.occupationService = occupationService;
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

    @Transactional
    public TurnResult endTurn(long gameId, long playerId) {
        val game = getGame(gameId);
        val player = playerService.getPlayer(playerId);
        if (!player.equals(game.getActivePlayer())) {
            throw new IllegalArgumentException(
                "Player is not active and thus cannot end the turn!");
        }

        val card = drawCardIfAllowedTo(game, player);

        PlayerEntity nextPlayer = null;
        if (card.isPresent() && CardService.VALAR_MORGHULIS.equals(card.get().getTerritory())) {
            game.setState(GameState.VALAR_MORGHULIS);
        } else {
            nextPlayer = playerService.getNextPlayer(game, playerId);
            game.setActivePlayer(nextPlayer);
        }
        gameRepository.save(game);
        return new TurnResult(nextPlayer, card.orElse(null));
    }

    @Transactional
    public Optional<Card> drawCardIfAllowedTo(GameEntity game, PlayerEntity player) {
        if (occupationService.hadOccupationsInRound(game.getId(), player.getId(), game.getRound())) {
            return Optional.of(cardService.drawNextCard(game, player));
        } else {
            return Optional.empty();
        }
    }

    public GameEntity getGame(long gameId) {
        return gameRepository.findById(gameId)
            .orElseThrow(() -> new ResourceNotFoundException("Game", gameId));
    }

    public void setState(long gameId, GameState state) {
        val game = getGame(gameId);
        game.setState(state);
        gameRepository.save(game);
    }
}
