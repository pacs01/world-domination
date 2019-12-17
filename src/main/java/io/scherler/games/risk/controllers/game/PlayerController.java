package io.scherler.games.risk.controllers.game;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.risk.controllers.defaults.NestedResourceAssembler;
import io.scherler.games.risk.controllers.defaults.NestedResourceController;
import io.scherler.games.risk.models.response.game.PlayerInfo;
import io.scherler.games.risk.services.game.PlayerService;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games/{gameId}/players")
public class PlayerController implements NestedResourceController<PlayerInfo> {

    private final NestedResourceAssembler<PlayerInfo> resourceAssembler;
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
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

    // todo implement create() method
}
