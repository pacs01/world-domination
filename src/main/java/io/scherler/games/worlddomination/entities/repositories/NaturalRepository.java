package io.scherler.games.worlddomination.entities.repositories;

import io.scherler.games.worlddomination.entities.NaturalIdentifiable;
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
