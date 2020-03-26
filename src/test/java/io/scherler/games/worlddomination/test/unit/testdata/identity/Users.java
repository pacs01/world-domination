package io.scherler.games.worlddomination.test.unit.testdata.identity;

import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import io.scherler.games.worlddomination.test.unit.testdata.game.Games;
import io.scherler.games.worlddomination.test.unit.testdata.game.Players;
import io.scherler.games.worlddomination.test.unit.testdata.map.Maps;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.val;

public enum Users {
    USER_ONE("userOne"),
    USER_TWO("userTwo"),
    USER_THREE("userThree"),
    USER_FOUR("userFour");

    private final String name;

    Users(String name) {
        this.name = name;
    }

    public UserAccountEntity toUpstreamEntity() {
        val userAccount = new UserAccountEntity(name);
        userAccount.setId(Integer.toUnsignedLong(this.ordinal()));

        return userAccount;
    }

    public UserAccountEntity toEntity() {
        return toEntity(this.toUpstreamEntity());
    }

    public UserAccountEntity toEntity(UserAccountEntity userAccount) {
        val games = Games.getAllForUserAccount(userAccount);
        userAccount.setGames(games);
        val players = Players.getAllForUserAccount(userAccount);
        userAccount.setPlayers(players);
        val maps = Maps.getAllForUserAccount(userAccount);
        userAccount.setMaps(maps);

        return userAccount;
    }

    public static Set<UserAccountEntity> getAll() {
        return Arrays.stream(Users.values())
            .map(Users::toUpstreamEntity)
            .collect(Collectors.toSet());
    }
}
