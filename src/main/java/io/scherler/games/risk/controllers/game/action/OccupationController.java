package io.scherler.games.risk.controllers.game.action;

import io.scherler.games.risk.models.request.Territory;
import io.scherler.games.risk.services.game.OccupationService;
import io.scherler.games.risk.services.game.action.ActionService;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/games/{gameId}/players/{playerId}/occupations")
public class OccupationController {

    private final ActionService actionService;
    private final OccupationService occupationService;

    public OccupationController(ActionService actionService, OccupationService occupationService) {
        this.actionService = actionService;
        this.occupationService = occupationService;
    }

    @PostMapping()
    public ResponseEntity<?> occupy(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @Valid @RequestBody Territory territory) {
        val occupationResult = actionService.occupy(territory, gameId, playerId);
        return ResponseEntity.ok().body(occupationResult); //todo add hateoas
    }

    @GetMapping()
    public ResponseEntity<?> getAll(@PathVariable("gameId") Long gameId) {
        val occupations = occupationService.getTerritoryInfos(gameId);
        return ResponseEntity.ok().body(occupations); //todo add hateoas
    }
}
