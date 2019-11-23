package io.scherler.games.risk.controllers.game.action;

import io.scherler.games.risk.services.game.GameService;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games/{gameId}/players/{playerId}/turns")
public class TurnController {

    private final GameService gameService;

    public TurnController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping()
    public ResponseEntity<?> endTurn(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId) {
        val turnResult = gameService.endTurn(gameId, playerId);
        return ResponseEntity.ok().body(turnResult); //todo add hateoas
    }
}
