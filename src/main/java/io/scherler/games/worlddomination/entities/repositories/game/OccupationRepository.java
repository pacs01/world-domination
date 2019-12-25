package io.scherler.games.worlddomination.entities.repositories.game;

import io.scherler.games.worlddomination.entities.game.OccupationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupationRepository extends JpaRepository<OccupationEntity, Long> {

    List<OccupationEntity> findByGameId(Long gameId);

    List<OccupationEntity> findByGameIdAndPlayerId(Long gameId, Long playerId);

    List<OccupationEntity> findByGameIdAndTerritoryName(Long gameId, String territoryName);

    List<OccupationEntity> findByGameIdAndPlayerIdAndTerritoryName(Long gameId, Long playerId,
        String territoryName);

    List<OccupationEntity> findByGameIdAndPlayerIdIsNotAndTerritoryName(Long gameId,
        Long playerId, String territoryName);

    List<OccupationEntity> findByGameIdAndPlayerIdAndOccupiedInRound(Long gameId, Long playerId,
        int occupiedInRound);
}
