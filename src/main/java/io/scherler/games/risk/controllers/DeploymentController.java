package io.scherler.games.risk.controllers;

import io.scherler.games.risk.models.Deployment;
import io.scherler.games.risk.services.UnitService;
import javax.validation.Valid;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players/{playerId}/deployments")
public class DeploymentController {

    private final UnitService unitService;

    public DeploymentController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping()
    public ResponseEntity<?> deploy(@PathVariable("playerId") Long playerId, @Valid @RequestBody Deployment deployment) {
        val deploymentResult = unitService.deploy(deployment, playerId);
        return ResponseEntity.ok().body(deploymentResult); //todo add hateoas
    }
}
