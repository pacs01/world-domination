package io.scherler.games.risk.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.scherler.games.risk.models.GameState;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "game")
@ToString(exclude = "map")
@EqualsAndHashCode(callSuper = true, exclude = "map")
@NoArgsConstructor
public class GameEntity extends BaseEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "mapId")
    private MapEntity map;

    private GameState state;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<PlayerEntity> playerEntities = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<OccupationEntity> occupationEntities = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<CardEntity> cardEntities = new HashSet<>();

    public GameEntity(String name, MapEntity map) {
        this.name = name;
        this.map = map;
        this.state = GameState.ACTIVE;
    }

    @ManyToOne
    private PlayerEntity activePlayer;

    public void addPlayers(List<PlayerEntity> players) {
        players.forEach(this::addPlayer);
    }

    public void addPlayer(PlayerEntity player) {
        playerEntities.add(player);
        player.setGame(this);
    }

    public void removePlayer(PlayerEntity player) {
        playerEntities.remove(player);
        player.setGame(null);
    }
}
