package io.scherler.games.risk.entities.game;

import io.scherler.games.risk.entities.BaseEntity;
import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.map.MapEntity;
import io.scherler.games.risk.models.GameState;
import java.util.HashSet;
import java.util.List;
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
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@Table(name = "game", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "creatorId"}))
@ToString(exclude = {"map", "creator"})
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class GameEntity extends BaseEntity {

    // todo: check @ToString(excludes...) on all entities

    @NaturalId
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creatorId")
    @EqualsAndHashCode.Include
    private UserAccountEntity creator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mapId")
    private MapEntity map;

    @Column(nullable = false)
    private GameState state;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<PlayerEntity> players = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<OccupationEntity> occupations = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<CardEntity> cards = new HashSet<>();

    @Setter(AccessLevel.NONE)
    private int round;

    @ManyToOne
    private PlayerEntity activePlayer;

    public GameEntity(String name, UserAccountEntity creator, MapEntity map) {
        this.name = name;
        this.creator = creator;
        this.map = map;
        this.state = GameState.INITIALISATION;
        round = 0;
    }

    public int increaseRound() {
        return ++round;
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

    public boolean hasStarted() {
        return !this.state.equals(GameState.INITIALISATION);
    }
}
