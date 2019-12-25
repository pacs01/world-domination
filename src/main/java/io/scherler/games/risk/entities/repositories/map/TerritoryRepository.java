package io.scherler.games.risk.entities.repositories.map;

import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.NaturalRepository;
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
