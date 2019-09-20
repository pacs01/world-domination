package io.scherler.games.risk.entities.repositories;

import io.scherler.games.risk.entities.TerritoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerritoryRepository extends JpaRepository<TerritoryEntity, Long> {

    List<TerritoryEntity> findByName(String name);
}
