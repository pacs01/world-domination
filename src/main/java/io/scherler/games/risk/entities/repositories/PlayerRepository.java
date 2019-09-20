package io.scherler.games.risk.entities.repositories;

import io.scherler.games.risk.entities.GameEntity;
import io.scherler.games.risk.entities.PlayerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    List<PlayerEntity> findByGameAndPosition(GameEntity gameEntity, int position);
}
