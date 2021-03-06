package io.scherler.games.worlddomination.entities.repositories.map;

import io.scherler.games.worlddomination.entities.map.ContinentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinentRepository extends JpaRepository<ContinentEntity, Long> {

    List<ContinentEntity> findByMapIdAndName(Long mapId, String name);
}
