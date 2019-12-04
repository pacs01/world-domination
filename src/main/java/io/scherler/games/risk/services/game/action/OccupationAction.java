package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.models.request.Territory;
import io.scherler.games.risk.models.response.TerritoryInfo;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.OccupationService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.models.RequestContext;
import io.scherler.games.risk.services.map.TerritoryService;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class OccupationAction extends ActionStrategy<Territory, TerritoryInfo> {

    private final TerritoryService territoryService;
    private final OccupationService occupationService;

    private TerritoryEntity target;

    public OccupationAction(GameService gameService, PlayerService playerService,
        TerritoryService territoryService, OccupationService occupationService) {
        super(gameService, playerService);
        this.territoryService = territoryService;
        this.occupationService = occupationService;
    }

    @Override
    protected void buildActionContext(RequestContext<Territory> context) {
        target = territoryService
            .getTerritory(context.getGame().getMap().getId(), context.getRequest().getName());
    }

    @Override
    protected void validateActionContext(RequestContext<Territory> context) {
        Validations.validateTerritoryNotOccupied(occupationService, context.getGame().getId(), target);
    }

    @Override
    protected TerritoryInfo apply(RequestContext<Territory> context) {
        val createdOccupation = occupationService
            .add(context.getGame(), context.getPlayer(), target, 1);

        return new TerritoryInfo(target.getName(),
            createdOccupation.getPlayer().getColor().toString(), createdOccupation.getUnits());
    }
}
