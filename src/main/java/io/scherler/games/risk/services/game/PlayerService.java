package io.scherler.games.risk.services.game;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.repositories.game.PlayerRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.PlayerColor;
import io.scherler.games.risk.models.request.UserAccount;
import io.scherler.games.risk.models.request.UserRequest;
import io.scherler.games.risk.services.identity.UserAccountService;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class PlayerService extends CrudService<PlayerEntity, UserRequest<GameEntity>> {

    private final PlayerRepository playerRepository;
    private final UserAccountService userAccountService;

    public PlayerService(PlayerRepository playerRepository, UserAccountService userAccountService) {
        super(playerRepository);
        this.playerRepository = playerRepository;
        this.userAccountService = userAccountService;
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

        val position = game.getPlayers().size() + 1;
        if (position > PlayerColor.values().length) {
            throw new IllegalArgumentException(
                "Not enough colors available for '" + position + "' players.");
        }

        val userAccount = request.getUserAccount();
        val newPlayer = new PlayerEntity(game, userAccount, position,
            PlayerColor.values()[position - 1], 10);

        return entityRepository.save(newPlayer);
    }

    //todo: rewrite this method so that users don't have to be provided at game creation
    List<PlayerEntity> generatePlayers(GameEntity game, int number) {
        if (number > PlayerColor.values().length) {
            throw new IllegalArgumentException(
                "Not enough colors available for '" + number + "' players.");
        }

        val playerList = new ArrayList<PlayerEntity>();
        for (int i = 0; i < number; i++) {
            playerList.add(new PlayerEntity(game,
                userAccountService.createNew(new UserAccount(game.getName() + "-user-" + i)), i,
                PlayerColor.values()[i], 10));
        }

        return playerList;
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
}
