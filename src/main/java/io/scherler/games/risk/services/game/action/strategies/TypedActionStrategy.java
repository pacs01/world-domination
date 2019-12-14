package io.scherler.games.risk.services.game.action.strategies;

import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.models.context.TypedActionContext;
import io.scherler.games.risk.services.game.action.models.context.TypedRequestContext;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public abstract class TypedActionStrategy<RequestModelT, ResponseModelT> extends ActionStrategy<
    TypedRequestContext<RequestModelT>, TypedActionContext<RequestModelT>, ResponseModelT> {

    public TypedActionStrategy(GameService gameService,
        PlayerService playerService) {
        super(gameService, playerService);
    }

    @Override
    protected TypedActionContext<RequestModelT> buildActionContext(
        TypedRequestContext<RequestModelT> requestContext) {
        val game = gameService.get(requestContext.getGameId());
        val player = playerService.get(requestContext.getPlayerId());
        return new TypedActionContext<>(game, player, requestContext.getRequest());
    }
}
