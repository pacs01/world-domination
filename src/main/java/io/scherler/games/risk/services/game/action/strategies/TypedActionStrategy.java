package io.scherler.games.risk.services.game.action.strategies;

import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.models.context.TypedActionContext;
import io.scherler.games.risk.services.game.action.models.context.TypedRequestContext;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public abstract class TypedActionStrategy<RequestModel, ResponseModel> extends
    ActionStrategy<TypedRequestContext<RequestModel>, TypedActionContext<RequestModel>, ResponseModel> {

    public TypedActionStrategy(GameService gameService,
        PlayerService playerService) {
        super(gameService, playerService);
    }

    @Override
    protected TypedActionContext<RequestModel> buildActionContext(
        TypedRequestContext<RequestModel> requestContext) {
        val game = gameService.getGame(requestContext.getGameId());
        val player = playerService.getPlayer(requestContext.getPlayerId());
        return new TypedActionContext<>(game, player, requestContext.getRequest());
    }
}
