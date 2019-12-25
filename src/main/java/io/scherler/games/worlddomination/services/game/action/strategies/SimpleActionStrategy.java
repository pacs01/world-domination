package io.scherler.games.worlddomination.services.game.action.strategies;

import io.scherler.games.worlddomination.services.game.GameService;
import io.scherler.games.worlddomination.services.game.PlayerService;
import io.scherler.games.worlddomination.services.game.action.models.context.ActionContext;
import io.scherler.games.worlddomination.services.game.action.models.context.RequestContext;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public abstract class SimpleActionStrategy<ResponseModelT> extends
    ActionStrategy<RequestContext, ActionContext, ResponseModelT> {

    public SimpleActionStrategy(GameService gameService,
        PlayerService playerService) {
        super(gameService, playerService);
    }

    @Override
    protected ActionContext buildActionContext(RequestContext requestContext) {
        val game = gameService.get(requestContext.getGameId());
        val player = playerService.get(requestContext.getPlayerId());
        return new ActionContext(game, player);
    }
}
