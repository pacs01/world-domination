package io.scherler.games.risk.services;

import io.scherler.games.risk.controllers.ResourceNotFoundException;
import io.scherler.games.risk.entities.GameEntity;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.entities.PlayerRepository;
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

    List<PlayerEntity> generatePlayers(GameEntity game, int number) {
        if (number > PlayerColor.values().length) {
            throw new IllegalArgumentException("Not enough colors available for '" + number + "' players.");
        }

        val playerList = new ArrayList<PlayerEntity>();
        for (int i = 0; i < number; i++) {
            playerList.add(new PlayerEntity(PlayerColor.values()[i], game));
        }

        return playerList;
    }

}
