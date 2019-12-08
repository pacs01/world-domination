package io.scherler.games.risk.services.game.action;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.models.GameState;
import io.scherler.games.risk.models.response.Card;
import io.scherler.games.risk.models.response.TurnResult;
import io.scherler.games.risk.services.game.CardService;
import io.scherler.games.risk.services.game.GameService;
import io.scherler.games.risk.services.game.OccupationService;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.game.action.models.context.ActionContext;
import java.util.Optional;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class EndTurnAction extends SimpleActionStrategy<TurnResult> {

    private final OccupationService occupationService;
    private final CardService cardService;

    public EndTurnAction(GameService gameService, PlayerService playerService,
        OccupationService occupationService,
        CardService cardService) {
        super(gameService, playerService);
        this.occupationService = occupationService;
        this.cardService = cardService;
    }

    @Override
    protected TurnResult apply(ActionContext context) {
        val card = drawCardIfAllowedTo(context.getGame(), context.getPlayer());

        if (card.isPresent() && CardService.VALAR_MORGHULIS.equals(card.get().getTerritory())) {
            gameService.endGame(context.getGame(), GameState.VALAR_MORGHULIS);
            return new TurnResult(null, card.get());
        } else {
            val updatedGame = gameService.endTurn(context.getGame());
            return new TurnResult(updatedGame.getActivePlayer(), card.orElse(null));
        }
    }

    private Optional<Card> drawCardIfAllowedTo(GameEntity game, PlayerEntity player) {
        if (occupationService
            .hadOccupationsInRound(game.getId(), player.getId(), game.getRound())) {
            return Optional.of(cardService.drawNextCard(game, player));
        } else {
            return Optional.empty();
        }
    }
}
