package io.scherler.games.risk.entities.repositories;

import io.scherler.games.risk.entities.MapEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<MapEntity, Long> {

    List<MapEntity> findByName(String name);
}
