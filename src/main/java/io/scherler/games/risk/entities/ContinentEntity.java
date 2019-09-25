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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "continent")
@ToString(exclude = "map")
@EqualsAndHashCode(callSuper = true, exclude = "map")
@NoArgsConstructor
public class ContinentEntity extends BaseEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "mapId")
    private MapEntity map;

    public ContinentEntity(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL)
    private Set<TerritoryEntity> territoryEntities = new HashSet<>();
}
