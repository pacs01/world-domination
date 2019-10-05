package io.scherler.games.risk.controllers.action;

import io.scherler.games.risk.models.request.Deployment;
import io.scherler.games.risk.services.game.ActionService;
import javax.validation.Valid;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
