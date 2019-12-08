package io.scherler.games.risk.controllers.game.action;

import io.scherler.games.risk.services.game.action.ActionService;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games/{gameId}/players/{playerId}/turns")
public class TurnController {

    private final ActionService actionService;

    public TurnController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping()
    public ResponseEntity<?> endTurn(@PathVariable("gameId") Long gameId,
        @PathVariable("playerId") Long playerId) {
        val turnResult = actionService.endTurn(gameId, playerId);
        return ResponseEntity.ok().body(turnResult); //todo add hateoas
    }
}
