package io.scherler.games.risk.services.map;

import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.repositories.game.PlayerRepository;
import io.scherler.games.risk.models.PlayerColor;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerEntity getPlayer(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("Player", playerId));
    }

    public PlayerEntity getNextPlayer(GameEntity game, Long playerId) {
        val player = getPlayer(playerId);
        int nextPosition = (player.getPosition() + 1) % game.getPlayerEntities().size();
        return playerRepository.findByGameAndPosition(game, nextPosition).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Player", "game = " + game.getId() + " and position = " + nextPosition));
    }

    List<PlayerEntity> generatePlayers(GameEntity game, int number) {
        if (number > PlayerColor.values().length) {
            throw new IllegalArgumentException("Not enough colors available for '" + number + "' players.");
        }

        val playerList = new ArrayList<PlayerEntity>();
        for (int i = 0; i < number; i++) {
            playerList.add(new PlayerEntity(i, PlayerColor.values()[i], game));
        }

        return playerList;
    }

}
