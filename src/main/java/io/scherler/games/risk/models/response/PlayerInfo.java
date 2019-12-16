package io.scherler.games.risk.models.response;

import io.scherler.games.risk.entities.game.PlayerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PlayerInfo extends IdentifiableResource {

    public PlayerInfo(String userAccount, String color, int position) {
        this.userAccount = userAccount;
        this.color = color;
        this.position = position;
    }

    private String userAccount;

    private String color;

    private int position;

    public static PlayerInfo from(PlayerEntity playerEntity) {
        return PlayerInfo.builder()
            .id(playerEntity.getId())
            .userAccount(playerEntity.getUserAccount().getName())
            .color(playerEntity.getColor().toString())
            .position(playerEntity.getPosition())
            .build();
    }
}
