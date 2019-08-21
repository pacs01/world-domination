package io.scherler.games.risk.entities;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    List<UnitEntity> findByTerritoryAndPlayer(TerritoryEntity territoryEntity, PlayerEntity playerEntity);

}
