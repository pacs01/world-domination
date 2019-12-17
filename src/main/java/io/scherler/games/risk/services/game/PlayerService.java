package io.scherler.games.risk.services.game;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.repositories.game.PlayerRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.PlayerColor;
import io.scherler.games.risk.models.request.identity.UserRequest;
import io.scherler.games.risk.services.CrudService;
import java.util.List;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class PlayerService extends CrudService<PlayerEntity, UserRequest<GameEntity>> {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        super(playerRepository);
        this.playerRepository = playerRepository;
    }

    @Override
    protected String getResourceName() {
        return "Player";
    }

    @Override
    public PlayerEntity create(UserRequest<GameEntity> request) {
        val game = request.getRequestObject();
        if (game.hasStarted()) {
            throw new IllegalStateException(
                "The Game has started already, no new players can be added!");
        }

        val position = game.getPlayers().size();
        if (position > PlayerColor.values().length - 1) {
            throw new IllegalArgumentException(
                "Not enough colors available for '" + position + "' players.");
        }

        val userAccount = request.getUserAccount();
        val newPlayer = new PlayerEntity(game, userAccount, position,
            PlayerColor.values()[position], 10);
        game.addPlayer(newPlayer);

        return entityRepository.save(newPlayer);
    }

    public PlayerEntity getNextPlayer(GameEntity game, Long currentPlayerId) {
        val currentPlayer = get(currentPlayerId);
        int nextPosition = (currentPlayer.getPosition() + 1) % game.getPlayers().size();
        return playerRepository.findByGameIdAndPosition(game.getId(), nextPosition).stream()
            .findFirst().orElseThrow(() -> new ResourceNotFoundException("Player",
                "game = " + game.getId() + " and position = " + nextPosition));
    }

    public PlayerEntity reduceDeployableUnits(PlayerEntity player, int numberOfUnits) {
        player.reduceDeployableUnits(numberOfUnits);
        return entityRepository.save(player);
    }

    public PlayerEntity getOneByIdAndGameId(Long playerId, Long gameId) {
        return playerRepository.findByIdAndGameId(playerId, gameId).stream().findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Player", playerId));
    }

    public List<PlayerEntity> getAllByGameId(Long gameId) {
        return playerRepository.findByGameId(gameId);
    }
}
