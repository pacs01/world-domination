package io.scherler.games.risk.controllers.game.action;

import io.scherler.games.risk.models.request.Movement;
import io.scherler.games.risk.services.game.action.ActionService;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/games/{gameId}/players/{playerId}/attacks")
public class AttackController {

    private final ActionService actionService;

    public AttackController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping()
    public ResponseEntity<?> attack(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @Valid @RequestBody Movement movement) {
        val attackInfo = actionService.attack(movement, gameId, playerId);
        return ResponseEntity.ok().body(attackInfo); //todo add hateoas
    }
}
