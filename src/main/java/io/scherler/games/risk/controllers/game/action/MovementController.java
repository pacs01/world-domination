package io.scherler.games.risk.controllers.game.action;

import io.scherler.games.risk.models.request.game.Movement;
import io.scherler.games.risk.services.game.action.ActionService;
import javax.validation.Valid;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games/{gameId}/players/{playerId}/movements")
public class MovementController {

    private final ActionService actionService;

    public MovementController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping
    public ResponseEntity<?> move(@PathVariable("gameId") Long gameId,
        @PathVariable("playerId") Long playerId, @Valid @RequestBody Movement movement) {
        val movementInfo = actionService.move(movement, gameId, playerId);
        return ResponseEntity.ok().body(movementInfo); //todo add hateoas
    }
}
