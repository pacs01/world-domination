package io.scherler.games.risk.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.risk.entities.BaseEntity;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class DefaultResourceAssembler<T extends BaseEntity> implements ResourceAssembler<T, Resource<T>> {

    private final DefaultResourceController<T> defaultResourceController;

    DefaultResourceAssembler(DefaultResourceController<T> defaultResourceController) {
        this.defaultResourceController = defaultResourceController;
    }

    @Override
    public Resource<T> toResource(T entity) {
        return new Resource<>(entity, linkTo(methodOn(defaultResourceController.getClass()).getOne(entity.getId())).withSelfRel(),
            linkTo(methodOn(defaultResourceController.getClass()).getAll()).withRel("list"));
    }
}
