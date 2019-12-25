package io.scherler.games.worlddomination.entities.repositories.map;

import io.scherler.games.worlddomination.entities.map.MapEntity;
import io.scherler.games.worlddomination.entities.repositories.NaturalRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends NaturalRepository<MapEntity, Long, String> {

}
