package io.scherler.games.risk.entities.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.scherler.games.risk.entities.BaseEntity;
import io.scherler.games.risk.entities.identity.UserAccountEntity;
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
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@Table(name = "player", uniqueConstraints = @UniqueConstraint(columnNames = {"gameId",
    "userAccountId"}))
@ToString(exclude = {"game", "userAccount"})
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class PlayerEntity extends BaseEntity {

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gameId")
    @EqualsAndHashCode.Include
    @JsonIgnore
    private GameEntity game;

    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userAccountId")
    @EqualsAndHashCode.Include
    @JsonIgnore
    private UserAccountEntity userAccount;

    private PlayerColor color;

    private int position;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<OccupationEntity> occupations = new HashSet<>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<CardEntity> cards = new HashSet<>();

    public PlayerEntity(GameEntity game, UserAccountEntity userAccount, int position,
        PlayerColor color) {
        this.game = game;
        this.userAccount = userAccount;
        this.position = position;
        this.color = color;
    }
}
