package io.scherler.games.risk.controllers.game.action;

import io.scherler.games.risk.models.request.Deployment;
import io.scherler.games.risk.services.game.action.ActionService;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/games/{gameId}/players/{playerId}/deployments")
public class DeploymentController {

    private final ActionService actionService;

    public DeploymentController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping()
    public ResponseEntity<?> deploy(@PathVariable("gameId") Long gameId, @PathVariable("playerId") Long playerId, @Valid @RequestBody Deployment deployment) {
        val deploymentResult = actionService.deploy(deployment, gameId, playerId);
        return ResponseEntity.ok().body(deploymentResult); //todo add hateoas
    }
}
