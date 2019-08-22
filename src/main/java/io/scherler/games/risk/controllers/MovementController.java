package io.scherler.games.risk.controllers;

import io.scherler.games.risk.models.Movement;
import io.scherler.games.risk.services.ActionService;
import javax.validation.Valid;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players/{playerId}/movements")
public class MovementController {

    private final ActionService actionService;

    public MovementController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping()
    public ResponseEntity<?> move(@PathVariable("playerId") Long playerId, @Valid @RequestBody Movement movement) {
        val movementResult = actionService.move(movement, playerId);
        return ResponseEntity.ok().body(movementResult); //todo add hateoas
    }
}
