package io.scherler.games.risk.entities.repositories;

import io.scherler.games.risk.entities.ContinentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinentRepository extends JpaRepository<ContinentEntity, Long> {

}
