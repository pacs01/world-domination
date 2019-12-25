package io.scherler.games.worlddomination.entities.repositories.identity;

import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

}
