package io.scherler.games.risk.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.scherler.games.risk.models.PlayerColor;
import java.util.HashSet;
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
@Table(name = "player")
@ToString(exclude = "game")
@EqualsAndHashCode(callSuper = true, exclude = "game")
@NoArgsConstructor
public class PlayerEntity extends BaseEntity {

    private PlayerColor color;

    public PlayerEntity(int position, PlayerColor color, GameEntity game) {
        this.position = position;
        this.color = color;
        this.game = game;
    }

    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "gameId")
    private GameEntity game;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<TerritoryEntity> territoryEntities = new HashSet<>();
}
