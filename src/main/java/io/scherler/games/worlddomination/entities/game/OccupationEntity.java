package io.scherler.games.worlddomination.entities.game;

import io.scherler.games.worlddomination.entities.map.TerritoryEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@Table(name = "occupation", uniqueConstraints = @UniqueConstraint(columnNames = {"gameId",
    "territoryId"}))
@ToString(exclude = {"player", "territory"})
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class OccupationEntity extends BaseGameEntity {

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "territoryId")
    @EqualsAndHashCode.Include
    private TerritoryEntity territory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playerId")
    private PlayerEntity player;

    @Setter(AccessLevel.NONE)
    private int units;

    private int occupiedInRound;

    public OccupationEntity(GameEntity game, TerritoryEntity territory, PlayerEntity player,
        int units, int currentRound) {
        game.addOccupation(this);
        territory.addOccupation(this);
        player.addOccupation(this);
        this.units = units;
        this.occupiedInRound = currentRound;
    }

    public int addUnits(int numberOfUnits) {
        units += numberOfUnits;
        return units;
    }

    public int removeUnits(int numberOfUnits) {
        if (units < numberOfUnits) {
            throw new IllegalArgumentException(
                "Not enough units available at territory '" + territory.getName() + "'.");
        }
        units -= numberOfUnits;
        return units;
    }

    @Transactional
    public void conquer(PlayerEntity player, int units) {
        if (this.units != 0) {
            throw new IllegalStateException("Territory " + territory.getName()
                + " can't be conquered because there are still units deployed.");
        }
        this.player = player;
        this.units = units;
    }

    public boolean isOccupiedBy(@NotNull PlayerEntity player) {
        return this.player != null && this.player.getId().equals(player.getId());
    }
}
