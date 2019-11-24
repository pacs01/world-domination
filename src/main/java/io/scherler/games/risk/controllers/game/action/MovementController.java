package io.scherler.games.risk.controllers.game.action;

import io.scherler.games.risk.models.request.Movement;
import io.scherler.games.risk.services.game.action.ActionService;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/games/{gameId}/players/{playerId}/movements")
public class MovementController {

    private final ActionService actionService;

    public MovementController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping()
    public ResponseEntity<?> move(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @Valid @RequestBody Movement movement) {
        val movementInfo = actionService.move(movement, gameId, playerId);
        return ResponseEntity.ok().body(movementInfo); //todo add hateoas
    }
}
