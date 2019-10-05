package io.scherler.games.risk.controllers.action;

import io.scherler.games.risk.models.request.Territory;
import io.scherler.games.risk.services.map.ActionService;
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
    public ResponseEntity<?> occupy(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @Valid @RequestBody Territory territory) {
        val occupationResult = actionService.occupy(territory, gameId, playerId);
        return ResponseEntity.ok().body(occupationResult); //todo add hateoas
    }
}
