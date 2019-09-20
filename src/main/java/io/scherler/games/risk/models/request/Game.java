package io.scherler.games.risk.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Number of players is mandatory")
    private Integer numberOfPlayers;

}
