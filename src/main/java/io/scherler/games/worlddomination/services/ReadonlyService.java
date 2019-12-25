package io.scherler.games.worlddomination.services;

import io.scherler.games.worlddomination.exceptions.ResourceNotFoundException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class ReadonlyService<E> {

    protected final JpaRepository<E, Long> entityRepository;

    public ReadonlyService(JpaRepository<E, Long> entityRepository) {
        this.entityRepository = entityRepository;
    }

    protected abstract String getResourceName();

    public E get(long id) {
        return entityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(getResourceName(), id));
    }

    public List<E> getAll() {
        return entityRepository.findAll();
    }
}
