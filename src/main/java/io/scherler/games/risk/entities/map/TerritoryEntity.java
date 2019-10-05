package io.scherler.games.risk.entities.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.scherler.games.risk.entities.BaseEntity;
import io.scherler.games.risk.entities.game.CardEntity;
import io.scherler.games.risk.entities.game.OccupationEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// todo set not-null on attributes

@Data
@Entity
@Table(name = "territory")
@ToString(exclude = {"continent", "adjacentTerritories"})
@EqualsAndHashCode(callSuper = true, exclude = {"continent", "adjacentTerritories"})
@NoArgsConstructor
public class TerritoryEntity extends BaseEntity {

    public TerritoryEntity(String name, ContinentEntity continent) {
        this.name = name;
        this.continent = continent;
    }

    @Column(unique=true)
    private String name;

    @ManyToMany
    @JsonIgnore
    private Set<TerritoryEntity> adjacentTerritories = new HashSet<>();

    public void addAdjacentTerritories(TerritoryEntity... territoryEntities) {
        for (TerritoryEntity territoryEntity : territoryEntities) {
            addAdjacentTerritory(territoryEntity);
        }
    }

    public void addAdjacentTerritory(TerritoryEntity territoryEntity) {
        adjacentTerritories.add(territoryEntity);
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "continentId")
    @JsonIgnore
    private ContinentEntity continent;

    @OneToMany(mappedBy = "territory", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OccupationEntity> occupations = new HashSet<>();

    @OneToMany(mappedBy = "territory", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CardEntity> cards = new HashSet<>();
}
