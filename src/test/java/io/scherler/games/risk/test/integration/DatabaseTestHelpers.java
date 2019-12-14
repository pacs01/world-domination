package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.models.request.UserAccount;
import io.scherler.games.risk.models.request.UserRequest;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.identity.UserAccountService;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class DatabaseTestHelpers {

    private final UserAccountService userAccountService;
    private final PlayerService playerService;

    public DatabaseTestHelpers(
        UserAccountService userAccountService,
        PlayerService playerService) {
        this.userAccountService = userAccountService;
        this.playerService = playerService;
    }

    List<PlayerEntity> generatePlayers(GameEntity game, int number) {
        val playerList = new ArrayList<PlayerEntity>();
        for (int i = 0; i < number; i++) {
            val userAccount = userAccountService
                .create(new UserAccount(game.getName() + "-user-" + i));
            playerList.add(playerService.create(new UserRequest<>(game, userAccount)));
        }

        return playerList;
    }

}
