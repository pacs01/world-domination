package io.scherler.games.risk.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// todo set not-null on attributes

@Data
@Entity
@Table(name = "card")
@ToString(exclude = {"game", "player", "territory"})
@EqualsAndHashCode(callSuper = true, exclude = {"game", "player", "territory"})
@NoArgsConstructor
public class CardEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "gameId")
    private GameEntity game;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "playerId")
    private PlayerEntity player;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "territoryId")
    private TerritoryEntity territory;

    public CardEntity(GameEntity game, PlayerEntity player, TerritoryEntity territory) {
        this.game = game;
        this.player = player;
        this.territory = territory;
    }
}
