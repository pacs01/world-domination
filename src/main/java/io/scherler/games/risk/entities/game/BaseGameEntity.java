package io.scherler.games.risk.entities.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.scherler.games.risk.entities.BaseEntity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"game"})
@MappedSuperclass
public abstract class BaseGameEntity extends BaseEntity {

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gameId")
    @EqualsAndHashCode.Include
    @JsonIgnore
    protected GameEntity game;
}
