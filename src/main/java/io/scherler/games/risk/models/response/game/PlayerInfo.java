package io.scherler.games.risk.models.response.game;

import io.scherler.games.risk.entities.game.PlayerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PlayerInfo extends NestedGameResource {

    public PlayerInfo(String userAccount, String color, int position) {
        this.userAccount = userAccount;
        this.color = color;
        this.position = position;
    }

    private String userAccount;

    private String color;

    private int position;

    public static PlayerInfo from(PlayerEntity playerEntity) {
        if (playerEntity == null) {
            return null;
        }
        return PlayerInfo.builder()
            .id(playerEntity.getId())
            .gameId(playerEntity.getGame().getId())
            .userAccount(playerEntity.getUserAccount().getName())
            .color(playerEntity.getColor().toString())
            .position(playerEntity.getPosition())
            .build();
    }
}
