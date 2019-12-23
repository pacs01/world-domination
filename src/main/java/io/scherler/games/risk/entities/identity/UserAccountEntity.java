package io.scherler.games.risk.entities.identity;

import io.scherler.games.risk.entities.BaseEntity;
import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.map.MapEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@Table(name = "useraccount")
@ToString(exclude = {"games", "maps", "players"})
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UserAccountEntity extends BaseEntity {

    @NaturalId
    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String name;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private Set<MapEntity> maps = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private Set<GameEntity> games = new HashSet<>();

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private Set<PlayerEntity> players = new HashSet<>();

    public UserAccountEntity(String name) {
        this.name = name;
    }

    public void addMap(MapEntity map) {
        maps.add(map);
        map.setCreator(this);
    }

    public void addGame(GameEntity game) {
        games.add(game);
        game.setCreator(this);
    }

    public void addPlayer(PlayerEntity player) {
        players.add(player);
        player.setUserAccount(this);
    }
}
