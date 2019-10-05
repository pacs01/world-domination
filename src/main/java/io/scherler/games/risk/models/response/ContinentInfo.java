package io.scherler.games.risk.models.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContinentInfo {

    private String name;

    private List<TerritoryInfo> territories;
}
