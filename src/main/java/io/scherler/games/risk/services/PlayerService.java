package io.scherler.games.risk.services;

import io.scherler.games.risk.controllers.ResourceNotFoundException;
import io.scherler.games.risk.entities.GameEntity;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.entities.PlayerRepository;
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

    List<PlayerEntity> generatePlayers(GameEntity game, int number) {
        val playerList = new ArrayList<PlayerEntity>();
        if (number > Color.values().length) {
            throw new IllegalArgumentException("Not enough colors available for '" + number + "' players.");
        }

        for (int i = 0; i < number; i++) {
            playerList.add(new PlayerEntity(Color.values()[i].name(), game));
        }

        return playerList;
    }

    PlayerEntity getPlayer(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException(playerId));
    }

}

enum Color {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    ORANGE,
    VIOLET
}
