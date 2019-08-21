package io.scherler.games.risk.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "unit")
@ToString(exclude={"player", "territory"})
@EqualsAndHashCode(callSuper = true, exclude={"player", "territory"})
public class UnitEntity extends BaseEntity {

    public UnitEntity() {
    }

    public UnitEntity(PlayerEntity player, TerritoryEntity territory) {
        this.player = player;
        this.territory = territory;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "playerId")
    private PlayerEntity player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "territoryId")
    private TerritoryEntity territory;

}
