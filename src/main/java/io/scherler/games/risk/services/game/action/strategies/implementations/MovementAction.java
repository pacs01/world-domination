package io.scherler.games.risk.services.game.action.strategies.implementations;

import io.scherler.games.risk.entities.game.OccupationEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.models.request.Movement;
import io.scherler.games.risk.models.response.MovementInfo;
import io.scherler.games.risk.models.response.TerritoryInfo;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.OccupationService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.Validations;
import io.scherler.games.risk.services.game.action.models.Route;
import io.scherler.games.risk.services.game.action.models.context.TypedActionContext;
import io.scherler.games.risk.services.game.action.strategies.TypedActionStrategy;
import io.scherler.games.risk.services.map.TerritoryService;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class MovementAction extends TypedActionStrategy<Movement, MovementInfo> {

    private final TerritoryService territoryService;
    private final OccupationService occupationService;

    private TerritoryEntity source;
    private TerritoryEntity target;
    private OccupationEntity sourceOccupation;
    private OccupationEntity targetOccupation;

    public MovementAction(GameService gameService, PlayerService playerService,
        TerritoryService territoryService, OccupationService occupationService) {
        super(gameService, playerService);
        this.territoryService = territoryService;
        this.occupationService = occupationService;
    }

    @Override
    protected void buildCustomActionContext(TypedActionContext<Movement> context) {
        source = territoryService
            .getTerritory(context.getGame().getMap().getId(), context.getRequest().getSource());
        target = territoryService
            .getTerritory(context.getGame().getMap().getId(), context.getRequest().getName());

        sourceOccupation = occupationService
            .getOccupationByPlayer(context.getGame().getId(), context.getPlayer().getId(),
                source.getName());
        targetOccupation = occupationService
            .getOccupationByPlayer(context.getGame().getId(), context.getPlayer().getId(),
                target.getName());
    }

    @Override
    protected void validateCustomActionContext(TypedActionContext<Movement> context) {
        Validations.validateNumberOfUnits(context.getRequest().getNumberOfUnits());
        Validations
            .validateRemainingUnits(sourceOccupation, context.getRequest().getNumberOfUnits());
        Validations
            .validateConnection(occupationService, context.getGame().getId(), sourceOccupation,
                targetOccupation);
    }

    @Override
    protected MovementInfo apply(TypedActionContext<Movement> context) {
        val updatedRoute = occupationService
            .moveUnits(new Route(sourceOccupation, targetOccupation),
                context.getRequest().getNumberOfUnits());

        return new MovementInfo(TerritoryInfo.from(updatedRoute.getSource()),
            TerritoryInfo.from(updatedRoute.getTarget()));
    }
}
