package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.models.request.Deployment;
import io.scherler.games.risk.models.request.Movement;
import io.scherler.games.risk.models.request.Territory;
import io.scherler.games.risk.models.response.AttackResult;
import io.scherler.games.risk.models.response.MovementInfo;
import io.scherler.games.risk.models.response.TerritoryInfo;
import io.scherler.games.risk.models.response.TurnResult;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

    private final OccupationAction occupationAction;

    private final DeploymentAction deploymentAction;

    private final MovementAction movementAction;

    private final AttackAction attackAction;

    private final EndTurnAction endTurnAction;

    public ActionService(OccupationAction occupationAction, DeploymentAction deploymentAction,
        MovementAction movementAction, AttackAction attackAction,
        EndTurnAction endTurnAction) {
        this.occupationAction = occupationAction;
        this.deploymentAction = deploymentAction;
        this.movementAction = movementAction;
        this.attackAction = attackAction;
        this.endTurnAction = endTurnAction;
    }

    @Transactional
    public TerritoryInfo occupy(Territory territory, long gameId, long playerId) {
        return occupationAction.execute(gameId, playerId, territory);
    }

    @Transactional
    public TerritoryInfo deploy(Deployment deployment, long gameId, long playerId) {
        return deploymentAction.execute(gameId, playerId, deployment);
    }

    @Transactional
    public MovementInfo move(Movement movement, long gameId, long playerId) {
        return movementAction.execute(gameId, playerId, movement);
    }

    @Transactional
    public AttackResult attack(Movement movement, long gameId, long playerId) {
        return attackAction.execute(gameId, playerId, movement);
    }

    @Transactional
    public TurnResult endTurn(long gameId, long playerId) {
        return endTurnAction.execute(gameId, playerId);
    }
}
