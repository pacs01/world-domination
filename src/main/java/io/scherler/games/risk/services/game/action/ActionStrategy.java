package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.models.context.ActionContext;
import io.scherler.games.risk.services.game.action.models.context.RequestContext;
import javax.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public abstract class ActionStrategy<RequestModel extends RequestContext, ActionModel extends ActionContext, ResponseModel> {

    protected final GameService gameService;
    protected final PlayerService playerService;

    public ActionStrategy(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @Transactional
    final ResponseModel execute(RequestModel requestContext) {
        val actionContext = buildActionContext(requestContext);
        validateActionContext(actionContext);
        buildCustomActionContext(actionContext);
        validateCustomActionContext(actionContext);
        return apply(actionContext);
    }

    abstract protected ActionModel buildActionContext(RequestModel requestContext);

    private void validateActionContext(ActionModel context) {
        //todo validate if player matches user
        Validations.validateActivePlayer(context);
    }

    protected void buildCustomActionContext(ActionModel context) {

    }

    protected void validateCustomActionContext(ActionModel context) {

    }

    abstract protected ResponseModel apply(ActionModel context);
}
