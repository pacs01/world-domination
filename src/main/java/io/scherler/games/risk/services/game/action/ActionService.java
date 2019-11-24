package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.models.request.Deployment;
import io.scherler.games.risk.models.request.Movement;
import io.scherler.games.risk.models.request.Territory;
import io.scherler.games.risk.models.response.AttackResult;
import io.scherler.games.risk.models.response.MovementInfo;
import io.scherler.games.risk.models.response.TerritoryInfo;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class ActionService {

    private final OccupationAction occupationAction;

    private final DeploymentAction deploymentAction;

    private final MovementAction movementAction;

    private final AttackAction attackAction;

    public ActionService(OccupationAction occupationAction, DeploymentAction deploymentAction, MovementAction movementAction, AttackAction attackAction) {
        this.occupationAction = occupationAction;
        this.deploymentAction = deploymentAction;
        this.movementAction = movementAction;
        this.attackAction = attackAction;
    }

    @Transactional
    public TerritoryInfo occupy(Territory territory, long gameId, long playerId) {
        return occupationAction.execute(territory, gameId, playerId);
    }

    @Transactional
    public TerritoryInfo deploy(Deployment deployment, long gameId, long playerId) {
        return deploymentAction.execute(deployment, gameId, playerId);
    }

    @Transactional
    public MovementInfo move(Movement movement, long gameId, long playerId) {
        return movementAction.execute(movement, gameId, playerId);
    }

    @Transactional
    public AttackResult attack(Movement movement, long gameId, long playerId) {
        return attackAction.execute(movement, gameId, playerId);
    }
}
