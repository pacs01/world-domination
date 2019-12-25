package io.scherler.games.worlddomination.services.game.action.strategies.implementations;

import io.scherler.games.worlddomination.entities.game.OccupationEntity;
import io.scherler.games.worlddomination.entities.map.TerritoryEntity;
import io.scherler.games.worlddomination.models.request.game.Deployment;
import io.scherler.games.worlddomination.models.response.map.TerritoryInfo;
import io.scherler.games.worlddomination.services.game.GameService;
import io.scherler.games.worlddomination.services.game.OccupationService;
import io.scherler.games.worlddomination.services.game.PlayerService;
import io.scherler.games.worlddomination.services.game.action.models.context.TypedActionContext;
import io.scherler.games.worlddomination.services.game.action.strategies.TypedActionStrategy;
import io.scherler.games.worlddomination.services.map.TerritoryService;
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
    protected void buildCustomActionContext(TypedActionContext<Deployment> context) {
        target = territoryService
            .getByName(context.getGame().getMap(), context.getRequest().getName());

        occupation = occupationService
            .getOccupationByPlayer(context.getGame().getId(), context.getPlayer().getId(),
                target.getName());
    }

    @Override
    protected TerritoryInfo apply(TypedActionContext<Deployment> context) {
        playerService
            .reduceDeployableUnits(context.getPlayer(), context.getRequest().getNumberOfUnits());
        val updatedOccupation = occupationService
            .addUnits(occupation, context.getRequest().getNumberOfUnits());
        return TerritoryInfo.from(updatedOccupation);
    }
}
