package io.scherler.games.risk.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.risk.controllers.defaults.DefaultResourceAssembler;
import io.scherler.games.risk.controllers.defaults.DefaultResourceController;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.entities.repositories.PlayerRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController implements DefaultResourceController<PlayerEntity> {

    private final PlayerRepository playerRepository;
    private final DefaultResourceAssembler<PlayerEntity> defaultResourceAssembler;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        this.defaultResourceAssembler = new DefaultResourceAssembler<>(this);
    }

    @GetMapping
    public Resources<Resource<PlayerEntity>> getAll() {
        List<Resource<PlayerEntity>> players = playerRepository.findAll().stream().map(defaultResourceAssembler::toResource).collect(Collectors.toList());

        return new Resources<>(players, linkTo(methodOn(PlayerController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public Resource<PlayerEntity> getOne(@PathVariable Long id) {
        return defaultResourceAssembler.toResource(playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player", id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        playerRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
