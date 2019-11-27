package io.scherler.games.risk.entities.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.scherler.games.risk.entities.BaseEntity;
import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.identity.UserAccountEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@Table(name = "map", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "creatorId"}))
@ToString(exclude = "creator")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class MapEntity extends BaseEntity {

    @NaturalId
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorId")
    @EqualsAndHashCode.Include
    @JsonIgnore
    private UserAccountEntity creator;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
    private Set<ContinentEntity> continents = new HashSet<>();

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<GameEntity> games = new HashSet<>();

    public MapEntity(String name, UserAccountEntity creator) {
        this.name = name;
        this.creator = creator;
    }
}
