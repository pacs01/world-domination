package io.scherler.games.worlddomination.models.response.identity;

import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import io.scherler.games.worlddomination.models.response.IdentifiableResource;
import io.scherler.games.worlddomination.models.response.game.GameInfo;
import io.scherler.games.worlddomination.models.response.game.PlayerInfo;
import io.scherler.games.worlddomination.models.response.map.MapInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserInfo extends IdentifiableResource {

    private String name;

    private List<PlayerInfo> players;

    private List<GameInfo> games;

    private List<MapInfo> maps;

    public static UserInfo from(UserAccountEntity userAccountEntity) {
        if (userAccountEntity == null) {
            return null;
        }
        return UserInfo.builder()
            .id(userAccountEntity.getId())
            .name(userAccountEntity.getName())
            .players(userAccountEntity.getPlayers().stream().map(PlayerInfo::from)
                .collect(Collectors.toList()))
            .games(userAccountEntity.getGames().stream().map(GameInfo::from)
                .collect(Collectors.toList()))
            .maps(userAccountEntity.getMaps().stream().map(MapInfo::from)
                .collect(Collectors.toList()))
            .build();
    }
}
