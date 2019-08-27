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
@Table(name = "continent")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ContinentEntity extends BaseEntity {

    private String name;

    public ContinentEntity(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL)
    private Set<TerritoryEntity> territoryEntities = new HashSet<>();
}
