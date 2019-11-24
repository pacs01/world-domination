package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.models.request.Deployment;
import io.scherler.games.risk.models.response.TerritoryInfo;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.OccupationService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.map.TerritoryService;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class DeploymentAction extends ActionStrategy<Deployment, TerritoryInfo> {

    private final TerritoryService territoryService;
    private final OccupationService occupationService;

    public DeploymentAction(GameService gameService, PlayerService playerService, TerritoryService territoryService, OccupationService occupationService) {
        super(gameService, playerService);
        this.territoryService = territoryService;
        this.occupationService = occupationService;
    }

    @Override
    protected void customValidation() {
        // todo add validation (number of units left to place)
    }

    @Override
    protected TerritoryInfo apply(ActionContext<Deployment> context) {
        val target = territoryService.getTerritory(context.getGame().getMap().getId(), context.getRequest().getName());

        val occupation = occupationService.getOccupationByPlayer(context.getGame().getId(), context.getPlayer().getId(), target.getName());

        val updatedOccupation = occupationService.addUnits(occupation, context.getRequest().getNumberOfUnits());
        return new TerritoryInfo(target.getName(), updatedOccupation.getPlayer().getColor().toString(), updatedOccupation.getUnits());
    }
}
