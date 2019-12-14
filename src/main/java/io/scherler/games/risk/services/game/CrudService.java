package io.scherler.games.risk.services.game;

import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract class CrudService<E, C> {

    protected final JpaRepository<E, Long> entityRepository;

    CrudService(
        JpaRepository<E, Long> entityRepository) {
        this.entityRepository = entityRepository;
    }

    protected abstract String getResourceName();

    @Transactional
    public abstract E create(C request);

    public E get(long id) {
        return entityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(getResourceName(), id));
    }

    public List<E> getAll() {
        return entityRepository.findAll();
    }

    public void delete(long id) {
        entityRepository.deleteById(id);
    }
}
