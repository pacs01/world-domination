package io.scherler.games.risk.entities.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.scherler.games.risk.entities.BaseEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.map.MapEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "useraccount")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserAccountEntity extends BaseEntity {

    public UserAccountEntity(String name) {
        this.name = name;
    }

    private String name;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<MapEntity> maps = new HashSet<>();

    @OneToMany(mappedBy = "useraccount", cascade = CascadeType.ALL)
    private Set<PlayerEntity> players = new HashSet<>();
}
