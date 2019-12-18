package io.scherler.games.risk.controllers.game;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.risk.controllers.defaults.NestedResourceAssembler;
import io.scherler.games.risk.controllers.defaults.NestedResourceController;
import io.scherler.games.risk.models.request.identity.UserRequest;
import io.scherler.games.risk.models.response.game.PlayerInfo;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.identity.UserAccountService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games/{gameId}/players")
public class PlayerController implements NestedResourceController<PlayerInfo> {

    private final NestedResourceAssembler<PlayerInfo> resourceAssembler;
    private final PlayerService playerService;
    private final GameService gameService;
    private final UserAccountService userAccountService;

    public PlayerController(PlayerService playerService,
        GameService gameService,
        UserAccountService userAccountService) {
        this.playerService = playerService;
        this.gameService = gameService;
        this.userAccountService = userAccountService;
        this.resourceAssembler = new NestedResourceAssembler<>(this);
    }

    @GetMapping
    public Resources<Resource<PlayerInfo>> getAll(@PathVariable("gameId") Long gameId) {
        val players = playerService.getAllByGameId(gameId).stream().map(PlayerInfo::from)
            .map(resourceAssembler::toResource).collect(Collectors.toList());

        return new Resources<>(players,
            linkTo(methodOn(GameController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{playerId}")
    public Resource<PlayerInfo> getOne(@PathVariable("gameId") Long gameId,
        @PathVariable Long playerId) {
        return resourceAssembler
            .toResource(PlayerInfo.from(playerService.getOneByIdAndGameId(playerId, gameId)));
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<?> delete(@PathVariable("gameId") Long gameId,
        @PathVariable Long playerId) {
        playerService.delete(playerId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<?> createNew(@PathVariable("gameId") Long gameId)
        throws URISyntaxException {
        val userAccount = userAccountService.get(1); //todo: load user from http authorization
        val game = gameService.get(gameId);
        val player = playerService.create(new UserRequest<>(game, userAccount));
        Resource<PlayerInfo> resource = resourceAssembler.toResource(PlayerInfo.from(player));

        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }
}
