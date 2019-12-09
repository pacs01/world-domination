package io.scherler.games.risk.services.game.action.strategies;

import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.Validations;
import io.scherler.games.risk.services.game.action.models.context.ActionContext;
import io.scherler.games.risk.services.game.action.models.context.RequestContext;
import javax.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public abstract class ActionStrategy<RequestModel extends RequestContext,
    ActionModel extends ActionContext, ResponseModel> {

    protected final GameService gameService;
    protected final PlayerService playerService;

    ActionStrategy(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @Transactional
    public final ResponseModel execute(RequestModel requestContext) {
        val actionContext = buildActionContext(requestContext);
        validateActionContext(actionContext);
        buildCustomActionContext(actionContext);
        validateCustomActionContext(actionContext);
        return apply(actionContext);
    }

    protected abstract ActionModel buildActionContext(RequestModel requestContext);

    private void validateActionContext(ActionModel context) {
        //todo validate if player matches user
        Validations.validateActivePlayer(context);
    }

    protected void buildCustomActionContext(ActionModel context) {

    }

    protected void validateCustomActionContext(ActionModel context) {

    }

    protected abstract ResponseModel apply(ActionModel context);
}
