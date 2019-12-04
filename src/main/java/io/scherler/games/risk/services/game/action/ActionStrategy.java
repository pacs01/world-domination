package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.models.RequestContext;
import javax.transaction.Transactional;
import lombok.val;

abstract class ActionStrategy<RequestModel, ResponseModel> {

    private final GameService gameService;
    private final PlayerService playerService;

    ActionStrategy(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @Transactional
    final ResponseModel execute(long gameId, long playerId, RequestModel requestModel) {
        val requestContext = buildRequestContext(gameId, playerId, requestModel);
        validateRequestContext(requestContext);
        buildActionContext(requestContext);
        validateActionContext(requestContext);
        return apply(requestContext);
    }

    private RequestContext<RequestModel> buildRequestContext(long gameId, long playerId, RequestModel requestModel) {
        val game = gameService.getGame(gameId);
        val player = playerService.getPlayer(playerId);
        return new RequestContext<>(game, player, requestModel);
    }

    private void validateRequestContext(RequestContext<RequestModel> context) {
        //todo validate if player matches user
        Validations.validateActivePlayer(context);
    }

    abstract protected void buildActionContext(RequestContext<RequestModel> context);

    abstract protected void validateActionContext(RequestContext<RequestModel> context);

    abstract protected ResponseModel apply(RequestContext<RequestModel> requestContext);
}
