package io.scherler.games.risk.controllers.game;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.risk.controllers.defaults.DefaultResourceAssembler;
import io.scherler.games.risk.controllers.defaults.DefaultResourceController;
import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.models.request.NewGame;
import io.scherler.games.risk.models.request.UserAccount;
import io.scherler.games.risk.models.request.UserRequest;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.identity.UserAccountService;
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
@RequestMapping("/games")
public class GameController implements DefaultResourceController<GameEntity> {

    private final DefaultResourceAssembler<GameEntity> defaultResourceAssembler;
    private final GameService gameService;
    private final UserAccountService userAccountService;

    public GameController(GameService gameService,
        UserAccountService userAccountService) {
        this.gameService = gameService;
        this.userAccountService = userAccountService;
        this.defaultResourceAssembler = new DefaultResourceAssembler<>(this);
    }

    @GetMapping()
    public Resources<Resource<GameEntity>> getAll() {
        List<Resource<GameEntity>> games = gameService.getAll().stream()
            .map(defaultResourceAssembler::toResource).collect(Collectors.toList());

        return new Resources<>(games,
            linkTo(methodOn(GameController.class).getAll()).withSelfRel());
    }

    @PostMapping()
    public ResponseEntity<?> createNew(@Valid @RequestBody NewGame newGame)
        throws URISyntaxException {
        val creator = userAccountService
            .create(new UserAccount("test")); //todo: load user from http authorization
        Resource<GameEntity> resource = defaultResourceAssembler
            .toResource(gameService.create(new UserRequest<>(newGame, creator)));

        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping("/{id}")
    public Resource<GameEntity> getOne(@PathVariable Long id) {
        return defaultResourceAssembler.toResource(gameService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        gameService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
