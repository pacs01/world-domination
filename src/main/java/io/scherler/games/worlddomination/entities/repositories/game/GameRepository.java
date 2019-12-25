package io.scherler.games.worlddomination.entities.repositories.game;

import io.scherler.games.worlddomination.entities.game.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

}
