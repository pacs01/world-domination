package io.scherler.games.risk.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "game")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class GameEntity extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<PlayerEntity> playerEntities = new HashSet<>();

    public GameEntity(String name) {
        this.name = name;
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
