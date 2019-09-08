package io.scherler.games.risk.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "territory")
@ToString(exclude = {"continent", "player", "adjacentTerritories"})
@EqualsAndHashCode(callSuper = true, exclude = {"continent", "player", "adjacentTerritories"})
@NoArgsConstructor
public class TerritoryEntity extends BaseEntity {

    public TerritoryEntity(String name, ContinentEntity continent) {
        this.name = name;
        this.continent = continent;
    }

    private String name;

    @Setter(AccessLevel.NONE)
    private int units;

    public int addUnits(int numberOfUnits) {
        units += numberOfUnits;
        return units;
    }

    public int removeUnits(int numberOfUnits) {
        if (units < numberOfUnits) {
            throw new IllegalArgumentException("Not enough units available at territory '" + name + "'.");
        }
        units -= numberOfUnits;
        return units;
    }

    @ManyToMany
    private Set<TerritoryEntity> adjacentTerritories = new HashSet<>();

    public void addAdjacentTerritories(TerritoryEntity... territoryEntities) {
        for (TerritoryEntity territoryEntity : territoryEntities) {
            addAdjacentTerritory(territoryEntity);
        }
    }

    public void addAdjacentTerritory(TerritoryEntity territoryEntity) {
        adjacentTerritories.add(territoryEntity);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "continentId")
    private ContinentEntity continent;

    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "playerId")
    private PlayerEntity player;

    @Transactional
    public void conquer(PlayerEntity player, int units) {
        if (this.units != 0) {
            throw new IllegalStateException("Territory " + this.name + " can't be conquered because there are still units deployed.");
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
