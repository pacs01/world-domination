package io.scherler.games.risk.controllers.defaults;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.risk.models.response.IdentifiableResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class DefaultResourceAssembler<T extends IdentifiableResource> implements
    ResourceAssembler<T, Resource<T>> {

    private final DefaultResourceController<T> defaultResourceController;

    public DefaultResourceAssembler(DefaultResourceController<T> defaultResourceController) {
        this.defaultResourceController = defaultResourceController;
    }

    @Override
    public Resource<T> toResource(T resource) {
        return new Resource<>(resource,
            linkTo(methodOn(defaultResourceController.getClass()).getOne(resource.getId()))
                .withSelfRel(),
            linkTo(methodOn(defaultResourceController.getClass()).getAll()).withRel("list"));
    }
}
