package io.scherler.games.risk.entities.repositories.game;

import io.scherler.games.risk.entities.game.CardEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    List<CardEntity> findByGameId(Long gameId);

    List<CardEntity> findByGameIdAndPlayerId(Long gameId, Long playerId);
}
