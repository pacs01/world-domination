package io.scherler.games.risk.entities.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.scherler.games.risk.entities.BaseEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;

import javax.persistence.*;
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
@Table(name = "occupation", uniqueConstraints = @UniqueConstraint(columnNames = {"gameId", "territoryId"}))
@ToString(exclude = {"game", "player", "territory"})
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class OccupationEntity extends BaseEntity {

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gameId")
    @EqualsAndHashCode.Include
    @JsonIgnore
    private GameEntity game;

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

    public OccupationEntity(GameEntity game, TerritoryEntity territory, PlayerEntity player, int units) {
        this.game = game;
        this.territory = territory;
        this.player = player;
        this.units = units;
    }

    public int addUnits(int numberOfUnits) {
        units += numberOfUnits;
        return units;
    }

    public int removeUnits(int numberOfUnits) {
        if (units < numberOfUnits) {
            throw new IllegalArgumentException("Not enough units available at territory '" + territory.getName() + "'.");
        }
        units -= numberOfUnits;
        return units;
    }

    @Transactional
    public void conquer(PlayerEntity player, int units) {
        if (this.units != 0) {
            throw new IllegalStateException("Territory " + territory.getName() + " can't be conquered because there are still units deployed.");
        }
        this.player = player;
        this.units = units;
    }

    public boolean isOccupied() {
        return player != null;
    }

    public boolean isOccupiedBy(@NotNull PlayerEntity player) {
        return this.player != null && this.player.getId().equals(player.getId());
    }
}
