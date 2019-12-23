package io.scherler.games.risk.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract class ImmutableService<E, C> extends ReadonlyService<E> {

    public ImmutableService(JpaRepository<E, Long> entityRepository) {
        super(entityRepository);
    }

    @Transactional
    public abstract E create(C request);
}
