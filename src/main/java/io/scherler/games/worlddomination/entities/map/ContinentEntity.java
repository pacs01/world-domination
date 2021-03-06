package io.scherler.games.worlddomination.entities.map;

import io.scherler.games.worlddomination.entities.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
@Table(name = "continent", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "mapId"}))
@ToString(exclude = "territories")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class ContinentEntity extends BaseEntity {

    @NaturalId
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mapId")
    @EqualsAndHashCode.Include
    private MapEntity map;

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL)
    private Set<TerritoryEntity> territories = new HashSet<>();

    public ContinentEntity(MapEntity map, String name) {
        map.addContinent(this);
        this.name = name;
    }

    void addTerritory(TerritoryEntity territory) {
        territories.add(territory);
        territory.setContinent(this);
    }
}
