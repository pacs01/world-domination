package io.scherler.games.risk.models.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapInfo {

    private String name;

    private List<ContinentInfo> continents;
}
