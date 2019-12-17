package io.scherler.games.risk.controllers.defaults;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.risk.models.response.game.NestedGameResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class NestedResourceAssembler<T extends NestedGameResource> implements
    ResourceAssembler<T, Resource<T>> {

    private final NestedResourceController<T> nestedResourceController;

    public NestedResourceAssembler(NestedResourceController<T> nestedResourceController) {
        this.nestedResourceController = nestedResourceController;
    }

    @Override
    public Resource<T> toResource(T resource) {
        return new Resource<>(resource,
            linkTo(methodOn(nestedResourceController.getClass())
                .getOne(resource.getGameId(), resource.getId())).withSelfRel(),
            linkTo(methodOn(nestedResourceController.getClass()).getAll(resource.getGameId()))
                .withRel("list"));
    }
}
