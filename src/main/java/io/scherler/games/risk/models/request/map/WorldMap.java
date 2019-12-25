package io.scherler.games.risk.models.request.map;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorldMap {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "A map must consist of at least one continent")
    private List<Continent> continents;
}
