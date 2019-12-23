package io.scherler.games.risk.entities.game;

import io.scherler.games.risk.entities.map.TerritoryEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@Table(name = "card", uniqueConstraints = @UniqueConstraint(columnNames = {"gameId",
    "territoryId"}))
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CardEntity extends BaseGameEntity {

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "territoryId")
    @EqualsAndHashCode.Include
    private TerritoryEntity territory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playerId")
    private PlayerEntity player;

    public CardEntity(GameEntity game, TerritoryEntity territory, PlayerEntity player) {
        game.addCard(this);
        player.addCard(this);
        territory.addCard(this);
    }
}
