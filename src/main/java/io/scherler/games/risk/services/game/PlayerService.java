package io.scherler.games.risk.services.game;

import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.repositories.game.PlayerRepository;
import io.scherler.games.risk.models.PlayerColor;
import java.util.ArrayList;
import java.util.List;

import io.scherler.games.risk.models.request.UserAccount;
import io.scherler.games.risk.services.identity.UserAccountService;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final UserAccountService userAccountService;

    public PlayerService(PlayerRepository playerRepository, UserAccountService userAccountService) {
        this.playerRepository = playerRepository;
        this.userAccountService = userAccountService;
    }

    public PlayerEntity getPlayer(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("Player", playerId));
    }

    public PlayerEntity getNextPlayer(GameEntity game, Long playerId) {
        val player = getPlayer(playerId);
        int nextPosition = (player.getPosition() + 1) % game.getPlayers().size();
        return playerRepository.findByGameIdAndPosition(game.getId(), nextPosition).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Player", "game = " + game.getId() + " and position = " + nextPosition));
    }

    //todo: rewrite this method so that users don't have to be provided at game creation
    List<PlayerEntity> generatePlayers(GameEntity game, int number) {
        if (number > PlayerColor.values().length) {
            throw new IllegalArgumentException("Not enough colors available for '" + number + "' players.");
        }

        val playerList = new ArrayList<PlayerEntity>();
        for (int i = 0; i < number; i++) {
            playerList.add(new PlayerEntity(game, userAccountService.createNew(new UserAccount(game.getName() + "-user-" + i)), i, PlayerColor.values()[i]));
        }

        return playerList;
    }

}
