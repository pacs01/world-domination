package io.scherler.games.risk.controllers;

import io.scherler.games.risk.models.Occupation;
import io.scherler.games.risk.services.ActionService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players/{playerId}/occupations")
public class OccupationController {

    private final ActionService actionService;

    public OccupationController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping()
    public ResponseEntity<?> occupy(@PathVariable("playerId") Long playerId, @Valid @RequestBody Occupation occupation) {
        actionService.occupy(occupation, playerId);
        return ResponseEntity.ok().build(); //todo add hateoas
    }
}
