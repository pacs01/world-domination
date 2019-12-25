package io.scherler.games.risk.entities.repositories.map;

import io.scherler.games.risk.entities.map.MapEntity;
import io.scherler.games.risk.entities.repositories.NaturalRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends NaturalRepository<MapEntity, Long, String> {

}
