package io.scherler.games.risk.entities.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.scherler.games.risk.entities.BaseEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// todo set not-null on attributes

@Data
@Entity
@Table(name = "occupation")
@ToString(exclude = {"game", "player", "territory"})
@EqualsAndHashCode(callSuper = true, exclude = {"game", "player", "territory"})
@NoArgsConstructor
public class OccupationEntity extends BaseEntity {

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

    @Setter(AccessLevel.NONE)
    private int units;

    public OccupationEntity(GameEntity game, PlayerEntity player, TerritoryEntity territory, int units) {
        this.game = game;
        this.player = player;
        this.territory = territory;
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
