package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.models.SimpleRequestContext;
import javax.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public abstract class SimpleActionStrategy<ResponseModel> {

    protected final GameService gameService;
    protected final PlayerService playerService;

    public SimpleActionStrategy(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @Transactional
    final ResponseModel execute(long gameId, long playerId) {
        val requestContext = buildRequestContext(gameId, playerId);
        validateRequestContext(requestContext);
        buildActionContext(requestContext);
        validateActionContext(requestContext);
        return apply(requestContext);
    }

    private SimpleRequestContext buildRequestContext(long gameId, long playerId) {
        val game = gameService.getGame(gameId);
        val player = playerService.getPlayer(playerId);
        return new SimpleRequestContext(game, player);
    }

    private void validateRequestContext(SimpleRequestContext context) {
        //todo validate if player matches user
        Validations.validateActivePlayer(context);
    }

    protected void buildActionContext(SimpleRequestContext context) {

    }

    protected void validateActionContext(SimpleRequestContext context) {

    }

    abstract protected ResponseModel apply(SimpleRequestContext requestContext);
}
