package io.scherler.games.risk.entities.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.scherler.games.risk.entities.BaseEntity;
import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.map.MapEntity;
import io.scherler.games.risk.models.GameState;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@Table(name = "game", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "creatorId"}))
@ToString(exclude = {"map", "creator"})
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class GameEntity extends BaseEntity {

    // todo: remove @JsonIgnore from all entities (solve with different models) and check @ToString(excludes...) on all entities

    @NaturalId
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creatorId")
    @EqualsAndHashCode.Include
    @JsonIgnore
    private UserAccountEntity creator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mapId")
    @JsonIgnore
    private MapEntity map;

    @Column(nullable = false)
    private GameState state;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<PlayerEntity> players = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OccupationEntity> occupations = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<CardEntity> cards = new HashSet<>();

    @ManyToOne
    private PlayerEntity activePlayer;

    public GameEntity(String name, UserAccountEntity creator, MapEntity map) {
        this.name = name;
        this.creator = creator;
        this.map = map;
        this.state = GameState.ACTIVE;
    }

    public void addPlayers(List<PlayerEntity> players) {
        players.forEach(this::addPlayer);
    }

    public void addPlayer(PlayerEntity player) {
        players.add(player);
        player.setGame(this);
    }

    public void removePlayer(PlayerEntity player) {
        players.remove(player);
        player.setGame(null);
    }
}
