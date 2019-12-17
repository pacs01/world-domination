package io.scherler.games.risk.controllers.defaults;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

public interface NestedResourceController<T> {

    Resource<T> getOne(Long parentId, Long id);

    Resources<Resource<T>> getAll(Long parentId);

}
