package io.scherler.games.worlddomination.controllers.game;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.worlddomination.controllers.defaults.DefaultResourceAssembler;
import io.scherler.games.worlddomination.controllers.defaults.DefaultResourceController;
import io.scherler.games.worlddomination.models.request.game.NewGame;
import io.scherler.games.worlddomination.models.request.identity.UserRequest;
import io.scherler.games.worlddomination.models.response.game.GameInfo;
import io.scherler.games.worlddomination.services.game.GameService;
import io.scherler.games.worlddomination.services.identity.UserAccountService;
import java.net.URI;
import java.net.URISyntaxException;
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
public class GameController implements DefaultResourceController<GameInfo> {

    private final DefaultResourceAssembler<GameInfo> resourceAssembler;
    private final GameService gameService;
    private final UserAccountService userAccountService;

    public GameController(GameService gameService,
        UserAccountService userAccountService) {
        this.gameService = gameService;
        this.userAccountService = userAccountService;
        this.resourceAssembler = new DefaultResourceAssembler<>(this);
    }

    @Override
    @GetMapping
    public Resources<Resource<GameInfo>> getAll() {
        val games = gameService.getAll().stream().map(GameInfo::from)
            .map(resourceAssembler::toResource).collect(Collectors.toList());

        return new Resources<>(games,
            linkTo(methodOn(GameController.class).getAll()).withSelfRel());
    }

    @Override
    @GetMapping("/{id}")
    public Resource<GameInfo> getOne(@PathVariable Long id) {
        return resourceAssembler.toResource(GameInfo.from(gameService.get(id)));
    }

    @PostMapping
    public ResponseEntity<?> createNew(@Valid @RequestBody NewGame newGame)
        throws URISyntaxException {
        val creator = userAccountService.get(1); //todo: load user from http authorization
        val game = gameService.create(new UserRequest<>(newGame, creator));
        Resource<GameInfo> resource = resourceAssembler
            .toResource(GameInfo.from(game));

        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        gameService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
