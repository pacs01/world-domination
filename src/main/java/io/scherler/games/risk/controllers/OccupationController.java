package io.scherler.games.risk.controllers;

import io.scherler.games.risk.models.request.Occupation;
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
@RequestMapping("/games/{gameId}/players/{playerId}/occupations")
public class OccupationController {

    private final ActionService actionService;

    public OccupationController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping()
    public ResponseEntity<?> occupy(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @Valid @RequestBody Occupation occupation) {
        val occupationResult = actionService.occupy(occupation, gameId, playerId);
        return ResponseEntity.ok().body(occupationResult); //todo add hateoas
    }
}
