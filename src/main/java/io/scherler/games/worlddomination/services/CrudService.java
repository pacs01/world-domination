package io.scherler.games.worlddomination.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class CrudService<E, C> extends ImmutableService<E, C> {

    public CrudService(JpaRepository<E, Long> entityRepository) {
        super(entityRepository);
    }

    public void delete(long id) {
        entityRepository.deleteById(id);
    }
}
