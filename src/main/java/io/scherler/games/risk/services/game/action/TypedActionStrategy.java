package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.models.TypedRequestContext;
import javax.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public abstract class TypedActionStrategy<RequestModel, ResponseModel> {

    protected final GameService gameService;
    protected final PlayerService playerService;

    public TypedActionStrategy(GameService gameService, PlayerService playerService) {
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

    private TypedRequestContext<RequestModel> buildRequestContext(long gameId, long playerId,
        RequestModel requestModel) {
        val game = gameService.getGame(gameId);
        val player = playerService.getPlayer(playerId);
        return new TypedRequestContext<>(game, player, requestModel);
    }

    private void validateRequestContext(TypedRequestContext<RequestModel> context) {
        //todo validate if player matches user
        Validations.validateActivePlayer(context);
    }

    protected void buildActionContext(TypedRequestContext<RequestModel> context) {

    }

    protected void validateActionContext(TypedRequestContext<RequestModel> context) {

    }

    abstract protected ResponseModel apply(TypedRequestContext<RequestModel> requestContext);
}
