package io.scherler.games.risk.entities.repositories.game;

import io.scherler.games.risk.entities.game.PlayerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    List<PlayerEntity> findByGameId(Long gameId);

    List<PlayerEntity> findByIdAndGameId(Long id, Long gameId);

    List<PlayerEntity> findByGameIdAndPosition(Long gameId, int position);
}
