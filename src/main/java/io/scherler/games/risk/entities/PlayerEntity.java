package io.scherler.games.risk.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.ToString;

@Data
@Entity
@Table(name = "player")
@ToString(exclude = "game")
@EqualsAndHashCode(callSuper = true, exclude = "game")
public class PlayerEntity extends BaseEntity {

    private String color;

    public PlayerEntity() {
    }

    public PlayerEntity(String color, GameEntity game) {
        this.color = color;
        this.game = game;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "gameId")
    private GameEntity game;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<UnitEntity> unitEntities = new HashSet<>();
}
