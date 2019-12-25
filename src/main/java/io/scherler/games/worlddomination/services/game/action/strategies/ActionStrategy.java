package io.scherler.games.worlddomination.services.game.action.strategies;

import io.scherler.games.worlddomination.services.game.GameService;
import io.scherler.games.worlddomination.services.game.PlayerService;
import io.scherler.games.worlddomination.services.game.action.Validations;
import io.scherler.games.worlddomination.services.game.action.models.context.ActionContext;
import io.scherler.games.worlddomination.services.game.action.models.context.RequestContext;
import javax.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public abstract class ActionStrategy<RequestContextT extends RequestContext,
    ActionContextT extends ActionContext, ResponseModelT> {

    protected final GameService gameService;
    protected final PlayerService playerService;

    ActionStrategy(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @Transactional
    public final ResponseModelT execute(RequestContextT requestContext) {
        val actionContext = buildActionContext(requestContext);
        validateActionContext(actionContext);
        buildCustomActionContext(actionContext);
        validateCustomActionContext(actionContext);
        return apply(actionContext);
    }

    protected abstract ActionContextT buildActionContext(RequestContextT requestContext);

    private void validateActionContext(ActionContextT context) {
        //todo validate if player matches user
        Validations.validateActivePlayer(context);
    }

    protected void buildCustomActionContext(ActionContextT context) {

    }

    protected void validateCustomActionContext(ActionContextT context) {

    }

    protected abstract ResponseModelT apply(ActionContextT context);
}
