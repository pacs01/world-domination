package io.scherler.games.worlddomination.test.unit.testdata.game;

import static io.scherler.games.worlddomination.test.unit.testdata.game.Games.GAME_ONE;
import static io.scherler.games.worlddomination.test.unit.testdata.identity.Users.USER_FOUR;
import static io.scherler.games.worlddomination.test.unit.testdata.identity.Users.USER_ONE;
import static io.scherler.games.worlddomination.test.unit.testdata.identity.Users.USER_THREE;
import static io.scherler.games.worlddomination.test.unit.testdata.identity.Users.USER_TWO;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.game.PlayerEntity;
import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import io.scherler.games.worlddomination.models.PlayerColor;
import io.scherler.games.worlddomination.test.unit.testdata.identity.Users;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.val;

public enum Players {
    PLAYER_ONE(GAME_ONE, USER_ONE, PlayerColor.RED, 10),
    PLAYER_TWO(GAME_ONE, USER_TWO, PlayerColor.GREEN, 10),
    PLAYER_THREE(GAME_ONE, USER_THREE, PlayerColor.BLUE, 10),
    PLAYER_FOUR(GAME_ONE, USER_FOUR, PlayerColor.YELLOW, 10);

    private final Games game;
    private final Users userAccount;
    private final PlayerColor color;
    private final int unitsToDeploy;

    Players(Games game, Users userAccount, PlayerColor color, int unitsToDeploy) {
        this.game = game;
        this.userAccount = userAccount;
        this.color = color;
        this.unitsToDeploy = unitsToDeploy;
    }

    public PlayerEntity toUpstreamEntity() {
        val player = new PlayerEntity(game.toUpstreamEntity(), userAccount.toUpstreamEntity(), this.ordinal(),
            color, unitsToDeploy);
        player.setId(Integer.toUnsignedLong(this.ordinal()));

        return player;
    }

    public PlayerEntity toEntity() {
        return toEntity(this.toUpstreamEntity());
    }

    public PlayerEntity toEntity(PlayerEntity player) {
        val occupations = Occupations.getAllForPlayer(player);
        player.setOccupations(occupations);

        return player;
    }

    public static Set<PlayerEntity> getAll() {
        return Arrays.stream(Players.values())
            .map(Players::toUpstreamEntity)
            .collect(Collectors.toSet());
    }

    public static Set<PlayerEntity> getAllForGame(GameEntity game) {
        return Arrays.stream(Players.values())
            .map(Players::toUpstreamEntity)
            .filter(e -> e.getGame().equals(game))
            .peek(e -> e.setGame(game))
            .collect(Collectors.toSet());
    }

    public static Set<PlayerEntity> getAllForUserAccount(UserAccountEntity userAccount) {
        return Arrays.stream(Players.values())
            .map(Players::toUpstreamEntity)
            .filter(e -> e.getUserAccount().equals(userAccount))
            .peek(e -> e.setUserAccount(userAccount))
            .collect(Collectors.toSet());
    }
}
