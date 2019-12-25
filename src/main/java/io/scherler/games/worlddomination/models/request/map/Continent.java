package io.scherler.games.worlddomination.models.request.map;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Continent {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "A continent must consist of at least one territory")
    private List<Territory> territories;
}
