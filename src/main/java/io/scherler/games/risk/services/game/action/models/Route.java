package io.scherler.games.risk.services.game.action.models;

import io.scherler.games.risk.entities.game.OccupationEntity;
import java.util.Arrays;
import java.util.List;
import lombok.Value;
import lombok.val;
import org.springframework.util.Assert;

@Value
public class Route {

    OccupationEntity source;
    OccupationEntity target;

    public List<OccupationEntity> asList() {
        return Arrays.asList(source, target);
    }

    public static Route fromList(List<OccupationEntity> occupations, Route originalRoute) {
        Assert.isTrue(occupations.size() == 2,
            "fatal error: a route must have exactly two occupations");
        val source = occupations.stream().filter(
            o -> o.getTerritory().getName().equals(originalRoute.source.getTerritory().getName()))
            .findFirst().orElseThrow(() -> new IllegalArgumentException(
                "source territory '" + originalRoute.source.getTerritory().getName()
                    + "' not found in occupations list."));
        val target = occupations.stream().filter(
            o -> o.getTerritory().getName().equals(originalRoute.target.getTerritory().getName()))
            .findFirst().orElseThrow(() -> new IllegalArgumentException(
                "target territory '" + originalRoute.target.getTerritory().getName()
                    + "' not found in occupations list."));
        return new Route(source, target);
    }
}
