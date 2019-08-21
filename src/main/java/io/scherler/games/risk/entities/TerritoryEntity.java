package io.scherler.games.risk.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// todo set not-null on attributes

@Data
@Entity
@Table(name = "territory")
@ToString(exclude = {"continent", "adjacentTerritories"})
@EqualsAndHashCode(callSuper = true, exclude = {"continent", "adjacentTerritories"})
public class TerritoryEntity extends BaseEntity {

    public TerritoryEntity() {
    }

    public TerritoryEntity(String name, ContinentEntity continent) {
        this.name = name;
        this.continent = continent;
    }

    private String name;

    @OneToMany(mappedBy = "territory", cascade = CascadeType.ALL)
    private Set<UnitEntity> unitEntities = new HashSet<>();

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
}
