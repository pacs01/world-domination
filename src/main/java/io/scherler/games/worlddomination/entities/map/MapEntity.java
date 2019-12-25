package io.scherler.games.worlddomination.entities.map;

import io.scherler.games.worlddomination.entities.BaseEntity;
import io.scherler.games.worlddomination.entities.game.GameEntity;
import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
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
@Table(name = "map", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "creatorId"}))
@ToString(exclude = {"games", "continents", "territories"})
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class MapEntity extends BaseEntity {

    @NaturalId
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorId")
    @EqualsAndHashCode.Include
    private UserAccountEntity creator;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
    private Set<ContinentEntity> continents = new HashSet<>();

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
    private Set<TerritoryEntity> territories = new HashSet<>();

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
    private Set<GameEntity> games = new HashSet<>();

    public MapEntity(String name, UserAccountEntity creator) {
        this.name = name;
        creator.addMap(this);
    }

    public void addGame(GameEntity game) {
        games.add(game);
        game.setMap(this);
    }

    void addContinent(ContinentEntity continent) {
        continents.add(continent);
        continent.setMap(this);
    }

    void addTerritory(TerritoryEntity territory) {
        territories.add(territory);
        territory.setMap(this);
    }
}
