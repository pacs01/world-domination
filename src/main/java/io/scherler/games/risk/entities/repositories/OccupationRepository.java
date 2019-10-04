package io.scherler.games.risk.entities.repositories;

import io.scherler.games.risk.entities.OccupationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupationRepository extends JpaRepository<OccupationEntity, Long> {

    List<OccupationEntity> findByGameIdAndTerritoryName(Long game_id, String territory_name);

    List<OccupationEntity> findByGameIdAndPlayerIdAndTerritoryName(Long game_id, Long player_id, String territory_name);

    List<OccupationEntity> findByGameIdAndPlayerIdIsNotAndTerritoryName(Long game_id, Long player_id, String territory_name);
}
