package io.scherler.games.worlddomination.services.game.action.strategies.implementations;

import io.scherler.games.worlddomination.entities.game.OccupationEntity;
import io.scherler.games.worlddomination.entities.map.TerritoryEntity;
import io.scherler.games.worlddomination.models.request.game.Movement;
import io.scherler.games.worlddomination.models.response.game.AttackResult;
import io.scherler.games.worlddomination.models.response.game.MovementInfo;
import io.scherler.games.worlddomination.models.response.map.TerritoryInfo;
import io.scherler.games.worlddomination.services.game.DiceService;
import io.scherler.games.worlddomination.services.game.GameService;
import io.scherler.games.worlddomination.services.game.OccupationService;
import io.scherler.games.worlddomination.services.game.PlayerService;
import io.scherler.games.worlddomination.services.game.action.Validations;
import io.scherler.games.worlddomination.services.game.action.models.Parties;
import io.scherler.games.worlddomination.services.game.action.models.Route;
import io.scherler.games.worlddomination.services.game.action.models.context.TypedActionContext;
import io.scherler.games.worlddomination.services.game.action.strategies.TypedActionStrategy;
import io.scherler.games.worlddomination.services.map.TerritoryService;
import java.util.Collections;
import java.util.List;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class AttackAction extends TypedActionStrategy<Movement, AttackResult> {

    private static final int MAX_NUMBER_OF_ATTACK_DICES = 3;
    private static final int MAX_NUMBER_OF_DEFEND_DICES = 2;

    private final TerritoryService territoryService;
    private final OccupationService occupationService;
    private final DiceService diceService;

    private TerritoryEntity source;
    private TerritoryEntity target;
    private OccupationEntity sourceOccupation;
    private OccupationEntity targetOccupation;

    public AttackAction(GameService gameService, PlayerService playerService,
        TerritoryService territoryService, OccupationService occupationService,
        DiceService diceService) {
        super(gameService, playerService);
        this.territoryService = territoryService;
        this.occupationService = occupationService;
        this.diceService = diceService;
    }

    @Override
    protected void buildCustomActionContext(TypedActionContext<Movement> context) {
        source = territoryService
            .getByName(context.getGame().getMap(), context.getRequest().getSource());
        target = territoryService
            .getByName(context.getGame().getMap(), context.getRequest().getName());

        sourceOccupation = occupationService
            .getOccupationByPlayer(context.getGame().getId(), context.getPlayer().getId(),
                source.getName());
        targetOccupation = occupationService
            .getOccupationByEnemy(context.getGame().getId(), context.getPlayer().getId(),
                target.getName());
    }

    @Override
    protected void validateCustomActionContext(TypedActionContext<Movement> context) {
        Validations.validateNumberOfUnits(context.getRequest().getNumberOfUnits());
        Validations
            .validateRemainingUnits(sourceOccupation, context.getRequest().getNumberOfUnits());
        Validations
            .validateConnection(occupationService, context.getGame().getId(), sourceOccupation,
                targetOccupation);
    }

    @Override
    protected AttackResult apply(TypedActionContext<Movement> context) {
        val attackDices = diceService.rollDices(
            Math.min(context.getRequest().getNumberOfUnits(), MAX_NUMBER_OF_ATTACK_DICES));
        val defendDices = diceService
            .rollDices(Math.min(targetOccupation.getUnits(), MAX_NUMBER_OF_DEFEND_DICES));
        val parties = performAttacks(
            new Parties(context.getRequest().getNumberOfUnits(),
                targetOccupation.getUnits()),
            attackDices, defendDices);

        val updatedRoute = occupationService
            .applyAttackResult(new Route(sourceOccupation, targetOccupation), parties,
                context.getPlayer());

        val movementInfo = new MovementInfo(TerritoryInfo.from(updatedRoute.getSource()),
            TerritoryInfo.from(updatedRoute.getTarget()));
        return new AttackResult(movementInfo, attackDices, defendDices);
    }

    private Parties performAttacks(Parties parties, List<Integer> attackDices,
        List<Integer> defendDices) {
        attackDices.sort(Collections.reverseOrder());
        defendDices.sort(Collections.reverseOrder());

        for (int i = 0; i < Math.min(attackDices.size(), defendDices.size()); i++) {
            if (attackDices.get(i).compareTo(defendDices.get(i)) > 0) {
                parties.getDefenders().killUnit();
            } else {
                parties.getAttackers().killUnit();
            }
        }
        return parties;
    }
}
