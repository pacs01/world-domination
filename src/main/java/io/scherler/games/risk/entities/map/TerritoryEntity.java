package io.scherler.games.risk.entities.map;

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
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@Table(name = "territory", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "mapId"}))
@ToString(exclude = {"adjacentTerritories", "occupations", "cards"})
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class TerritoryEntity extends BaseEntity {

    @NaturalId
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mapId")
    @EqualsAndHashCode.Include
    private MapEntity map;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "continentId")
    @EqualsAndHashCode.Include
    private ContinentEntity continent;

    @ManyToMany
    private Set<TerritoryEntity> adjacentTerritories = new HashSet<>();

    @OneToMany(mappedBy = "territory", cascade = CascadeType.ALL)
    private Set<OccupationEntity> occupations = new HashSet<>();

    @OneToMany(mappedBy = "territory", cascade = CascadeType.ALL)
    private Set<CardEntity> cards = new HashSet<>();

    public TerritoryEntity(String name, MapEntity map, ContinentEntity continent) {
        this.name = name;
        map.addTerritory(this);
        continent.addTerritory(this);
    }

    public void addOccupation(OccupationEntity occupation) {
        occupations.add(occupation);
        occupation.setTerritory(this);
    }

    public void addCard(CardEntity card) {
        cards.add(card);
        card.setTerritory(this);
    }

    public void addAdjacentTerritories(TerritoryEntity... territoryEntities) {
        for (TerritoryEntity territoryEntity : territoryEntities) {
            addAdjacentTerritory(territoryEntity);
        }
    }

    public void addAdjacentTerritory(TerritoryEntity territoryEntity) {
        adjacentTerritories.add(territoryEntity);
    }
}
