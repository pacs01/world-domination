package io.scherler.games.risk.entities;

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
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    public UserEntity(String name) {
        this.name = name;
    }

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PlayerEntity> playerEntities = new HashSet<>();
}