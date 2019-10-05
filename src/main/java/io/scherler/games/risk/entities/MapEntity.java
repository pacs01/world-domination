package io.scherler.games.risk.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "map")
@ToString(exclude = "creator")
@EqualsAndHashCode(callSuper = true, exclude = "creator")
@NoArgsConstructor
public class MapEntity extends BaseEntity {

    @Column(unique=true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "creatorId")
    private UserAccountEntity creator;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
    private Set<ContinentEntity> continents = new HashSet<>();

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
    private Set<GameEntity> games = new HashSet<>();

    public MapEntity(String name, UserAccountEntity creator) {
        this.name = name;
        this.creator = creator;
    }
}
