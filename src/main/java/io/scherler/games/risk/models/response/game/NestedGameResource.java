package io.scherler.games.risk.models.response.game;

import io.scherler.games.risk.models.response.IdentifiableResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public abstract class NestedGameResource extends IdentifiableResource {

    private long gameId;
}
