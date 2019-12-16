package io.scherler.games.risk.models.response;

import io.scherler.games.risk.entities.game.GameEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GameInfo extends IdentifiableResource {

    private String name;

    private String creator;

    private String map;

    private String state;

    private List<PlayerInfo> players;

    private int round;

    private PlayerInfo activePlayer;

    public static GameInfo from(GameEntity gameEntity) {
        return GameInfo.builder()
            .id(gameEntity.getId())
            .name(gameEntity.getName())
            .creator(gameEntity.getCreator().getName())
            .map(gameEntity.getMap().getName())
            .state(gameEntity.getState().toString())
            .players(
                gameEntity.getPlayers().stream().map(PlayerInfo::from).collect(Collectors.toList()))
            .round(gameEntity.getRound())
            .activePlayer(PlayerInfo.from(gameEntity.getActivePlayer()))
            .build();
    }
}
