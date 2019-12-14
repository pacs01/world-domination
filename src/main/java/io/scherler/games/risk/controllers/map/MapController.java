package io.scherler.games.risk.controllers.map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.risk.controllers.defaults.DefaultResourceAssembler;
import io.scherler.games.risk.controllers.defaults.DefaultResourceController;
import io.scherler.games.risk.entities.map.MapEntity;
import io.scherler.games.risk.entities.repositories.map.MapRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.request.Map;
import io.scherler.games.risk.models.request.UserRequest;
import io.scherler.games.risk.services.identity.UserAccountService;
import io.scherler.games.risk.services.map.MapService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.val;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/maps")
public class MapController implements DefaultResourceController<MapEntity> {

    private final MapRepository mapRepository;
    private final MapService mapService;
    private final UserAccountService userAccountService;
    private final DefaultResourceAssembler<MapEntity> defaultResourceAssembler;

    public MapController(MapRepository mapRepository, MapService mapService,
        UserAccountService userAccountService) {
        this.mapRepository = mapRepository;
        this.mapService = mapService;
        this.userAccountService = userAccountService;
        this.defaultResourceAssembler = new DefaultResourceAssembler<>(this);
    }

    @GetMapping()
    public Resources<Resource<MapEntity>> getAll() {
        List<Resource<MapEntity>> maps = mapRepository.findAll().stream()
            .map(defaultResourceAssembler::toResource).collect(Collectors.toList());

        return new Resources<>(maps, linkTo(methodOn(MapController.class).getAll()).withSelfRel());
    }

    @PostMapping()
    public ResponseEntity<?> createNew(@PathVariable("userId") Long userId,
        @Valid @RequestBody Map newMap) throws URISyntaxException {
        val userAccount = userAccountService.get(userId);
        Resource<MapEntity> resource = defaultResourceAssembler
            .toResource(mapService.create(new UserRequest<>(newMap, userAccount)));

        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping("/{id}")
    public Resource<MapEntity> getOne(@PathVariable Long id) {
        return defaultResourceAssembler.toResource(
            mapRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Map", id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        mapRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
