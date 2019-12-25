package io.scherler.games.risk.entities.repositories;

import io.scherler.games.risk.entities.NaturalIdentifiable;
import java.io.Serializable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NaturalRepository<T, I extends Serializable, N extends Serializable> extends
    JpaRepository<T, I> {

    Optional<T> findBySimpleNaturalId(N naturalId);

    Optional<T> findByNaturalId(NaturalIdentifiable naturalId);
}
