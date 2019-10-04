package io.scherler.games.risk.entities.repositories;

import io.scherler.games.risk.entities.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccountEntity, Long> {

}
