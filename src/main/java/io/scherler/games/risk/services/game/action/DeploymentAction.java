package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.entities.game.OccupationEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.models.request.Deployment;
import io.scherler.games.risk.models.response.TerritoryInfo;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.OccupationService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.models.TypedRequestContext;
import io.scherler.games.risk.services.map.TerritoryService;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class DeploymentAction extends TypedActionStrategy<Deployment, TerritoryInfo> {

    private final TerritoryService territoryService;
    private final OccupationService occupationService;

    private TerritoryEntity target;
    private OccupationEntity occupation;

    public DeploymentAction(GameService gameService, PlayerService playerService,
        TerritoryService territoryService, OccupationService occupationService) {
        super(gameService, playerService);
        this.territoryService = territoryService;
        this.occupationService = occupationService;
    }

    @Override
    protected void buildActionContext(TypedRequestContext<Deployment> context) {
        target = territoryService
            .getTerritory(context.getGame().getMap().getId(), context.getRequest().getName());

        occupation = occupationService
            .getOccupationByPlayer(context.getGame().getId(), context.getPlayer().getId(),
                target.getName());
    }

    @Override
    protected TerritoryInfo apply(TypedRequestContext<Deployment> context) {
        playerService
            .reduceDeployableUnits(context.getPlayer(), context.getRequest().getNumberOfUnits());
        val updatedOccupation = occupationService
            .addUnits(occupation, context.getRequest().getNumberOfUnits());
        return new TerritoryInfo(target.getName(),
            updatedOccupation.getPlayer().getColor().toString(), updatedOccupation.getUnits());
    }
}
