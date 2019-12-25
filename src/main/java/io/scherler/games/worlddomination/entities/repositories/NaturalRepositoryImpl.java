package io.scherler.games.worlddomination.entities.repositories;

import io.scherler.games.worlddomination.entities.NaturalIdentifiable;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class NaturalRepositoryImpl<T, I extends Serializable, N extends Serializable> extends
    SimpleJpaRepository<T, I> implements NaturalRepository<T, I, N> {

    private final EntityManager entityManager;

    public NaturalRepositoryImpl(JpaEntityInformation<T, I> entityInformation,
        EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findBySimpleNaturalId(N naturalId) {
        return entityManager.unwrap(Session.class)
            .bySimpleNaturalId(this.getDomainClass())
            .loadOptional(naturalId);
    }

    @Override
    public Optional<T> findByNaturalId(NaturalIdentifiable naturalId) {
        Map<String, Object> naturalIds = naturalId.getIds();
        NaturalIdLoadAccess<T> loadAccess = entityManager.unwrap(Session.class)
            .byNaturalId(this.getDomainClass());
        naturalIds.forEach(loadAccess::using);
        return loadAccess.loadOptional();
    }
}
