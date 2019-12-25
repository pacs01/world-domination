package io.scherler.games.risk.controllers.map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.risk.controllers.defaults.DefaultResourceAssembler;
import io.scherler.games.risk.controllers.defaults.DefaultResourceController;
import io.scherler.games.risk.models.request.identity.UserRequest;
import io.scherler.games.risk.models.request.map.Map;
import io.scherler.games.risk.models.response.map.MapInfo;
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
public class MapController implements DefaultResourceController<MapInfo> {

    private final MapService mapService;
    private final UserAccountService userAccountService;
    private final DefaultResourceAssembler<MapInfo> resourceAssembler;

    public MapController(MapService mapService, UserAccountService userAccountService) {
        this.mapService = mapService;
        this.userAccountService = userAccountService;
        this.resourceAssembler = new DefaultResourceAssembler<>(this);
    }

    @Override
    @GetMapping
    public Resources<Resource<MapInfo>> getAll() {
        List<Resource<MapInfo>> maps = mapService.getAll().stream().map(MapInfo::from)
            .map(resourceAssembler::toResource).collect(Collectors.toList());

        return new Resources<>(maps, linkTo(methodOn(MapController.class).getAll()).withSelfRel());
    }

    @Override
    @GetMapping("/{id}")
    public Resource<MapInfo> getOne(@PathVariable Long id) {
        return resourceAssembler.toResource(MapInfo.from(mapService.get(id)));
    }

    @PostMapping
    public ResponseEntity<?> createNew(@PathVariable("userId") Long userId,
        @Valid @RequestBody Map newMap) throws URISyntaxException {
        val userAccount = userAccountService.get(userId);
        val map = mapService.create(new UserRequest<>(newMap, userAccount));
        Resource<MapInfo> resource = resourceAssembler
            .toResource(MapInfo.from(map));

        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        mapService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
