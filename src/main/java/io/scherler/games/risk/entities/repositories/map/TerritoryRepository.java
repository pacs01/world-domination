package io.scherler.games.risk.entities.repositories.map;

import io.scherler.games.risk.entities.map.TerritoryEntity;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerritoryRepository extends JpaRepository<TerritoryEntity, Long> {

    List<TerritoryEntity> findByContinentMapIdAndName(Long continent_map_id, String name);

    List<TerritoryEntity> findByContinentMapId(Long continent_map_id);

    List<TerritoryEntity> findByContinentMapIdAndIdNotIn(Long continent_map_id, Collection<Long> id);

    long countByContinentMapId(Long continent_map_id);
}
