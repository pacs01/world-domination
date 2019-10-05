package io.scherler.games.risk.entities.repositories.map;

import io.scherler.games.risk.entities.map.MapEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<MapEntity, Long> {

    List<MapEntity> findByName(String name);
}
