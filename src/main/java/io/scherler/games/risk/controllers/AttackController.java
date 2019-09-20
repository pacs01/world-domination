package io.scherler.games.risk.controllers;

import io.scherler.games.risk.models.request.Movement;
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
@RequestMapping("/players/{playerId}/attacks")
public class AttackController {

    private final ActionService actionService;

    public AttackController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping()
    public ResponseEntity<?> attack(@PathVariable("playerId") Long playerId, @Valid @RequestBody Movement movement) {
        val attackInfo = actionService.attack(movement, playerId);
        return ResponseEntity.ok().body(attackInfo); //todo add hateoas
    }
}
