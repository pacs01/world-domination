package io.scherler.games.risk.entities.repositories.game;

import io.scherler.games.risk.entities.game.OccupationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupationRepository extends JpaRepository<OccupationEntity, Long> {

    List<OccupationEntity> findByGameId(Long game_id);

    List<OccupationEntity> findByGameIdAndPlayerId(Long game_id, Long player_id);

    List<OccupationEntity> findByGameIdAndTerritoryName(Long game_id, String territory_name);

    List<OccupationEntity> findByGameIdAndPlayerIdAndTerritoryName(Long game_id, Long player_id,
        String territory_name);

    List<OccupationEntity> findByGameIdAndPlayerIdIsNotAndTerritoryName(Long game_id,
        Long player_id, String territory_name);

    List<OccupationEntity> findByGameIdAndPlayerIdAndOccupiedInRound(Long game_id, Long player_id,
        int occupiedInRound);
}
