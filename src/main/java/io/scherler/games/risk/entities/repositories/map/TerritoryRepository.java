package io.scherler.games.risk.entities.repositories.map;

import io.scherler.games.risk.entities.map.TerritoryEntity;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerritoryRepository extends JpaRepository<TerritoryEntity, Long> {

    List<TerritoryEntity> findByContinentMapIdAndName(Long continentMapId, String name);

    List<TerritoryEntity> findByContinentMapId(Long continentMapId);

    List<TerritoryEntity> findByContinentMapIdAndIdNotIn(Long continentMapId,
        Collection<Long> id);

    long countByContinentMapId(Long continentMapId);
}
