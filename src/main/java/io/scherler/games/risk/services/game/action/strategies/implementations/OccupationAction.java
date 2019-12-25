package io.scherler.games.risk.services.game.action.strategies.implementations;

import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.models.request.map.Territory;
import io.scherler.games.risk.models.response.map.TerritoryInfo;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.OccupationService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.Validations;
import io.scherler.games.risk.services.game.action.models.context.TypedActionContext;
import io.scherler.games.risk.services.game.action.strategies.TypedActionStrategy;
import io.scherler.games.risk.services.map.TerritoryService;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class OccupationAction extends TypedActionStrategy<Territory, TerritoryInfo> {

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
    protected void buildCustomActionContext(TypedActionContext<Territory> context) {
        target = territoryService
            .getByName(context.getGame().getMap(), context.getRequest().getName());
    }

    @Override
    protected void validateCustomActionContext(TypedActionContext<Territory> context) {
        Validations
            .validateTerritoryNotOccupied(occupationService, context.getGame().getId(), target);
    }

    @Override
    protected TerritoryInfo apply(TypedActionContext<Territory> context) {
        val createdOccupation = occupationService
            .create(context.getGame(), context.getPlayer(), target, 1);

        return TerritoryInfo.from(createdOccupation);
    }
}
