package io.scherler.games.risk.controllers.defaults;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

public interface DefaultResourceController<T> {

    Resource<T> getOne(Long id);

    Resources<Resource<T>> getAll();

}
