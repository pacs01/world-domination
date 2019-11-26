package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.PlayerService;
import lombok.val;

import javax.transaction.Transactional;

abstract class ActionStrategy<RequestModel, ResponseModel> {

    private final GameService gameService;
    private final PlayerService playerService;

    ActionStrategy(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @Transactional
    ResponseModel execute(RequestModel requestModel, long gameId, long playerId) {
        val context = buildContext(requestModel, gameId, playerId);
        validateContext();
        customValidation();
        return apply(context);
    }

    private ActionContext<RequestModel> buildContext(RequestModel requestModel, long gameId, long playerId) {
        val game = gameService.getGame(gameId);
        val player = playerService.getPlayer(playerId);
        return new ActionContext<>(requestModel, game, player);
    }

    private void validateContext() {
        //todo validate if player matches user
        //todo validate if it is this player's turn
    }

    abstract protected void customValidation();

    abstract protected ResponseModel apply(ActionContext<RequestModel> context);
}