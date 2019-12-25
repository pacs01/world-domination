package io.scherler.games.worlddomination.entities.repositories.map;

import io.scherler.games.worlddomination.entities.map.TerritoryEntity;
import io.scherler.games.worlddomination.entities.repositories.NaturalRepository;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TerritoryRepository extends NaturalRepository<TerritoryEntity, Long, String> {

    List<TerritoryEntity> findByContinentMapId(Long continentMapId);

    List<TerritoryEntity> findByContinentMapIdAndIdNotIn(Long continentMapId,
        Collection<Long> id);

    long countByContinentMapId(Long continentMapId);
}
