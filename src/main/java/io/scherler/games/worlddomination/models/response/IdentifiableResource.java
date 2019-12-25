package io.scherler.games.worlddomination.models.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class IdentifiableResource {

    private long id;
}
