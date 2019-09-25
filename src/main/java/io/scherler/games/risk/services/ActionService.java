package io.scherler.games.risk.services;

import io.scherler.games.risk.entities.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.TerritoryRepository;
import io.scherler.games.risk.models.request.Deployment;
import io.scherler.games.risk.models.request.Movement;
import io.scherler.games.risk.models.request.Occupation;
import io.scherler.games.risk.models.response.AttackResult;
import io.scherler.games.risk.models.response.MovementInfo;
import io.scherler.games.risk.models.response.TerritoryInfo;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

    private static final int MAX_NUMBER_OF_ATTACK_DICES = 3;
    private static final int MAX_NUMBER_OF_DEFEND_DICES = 2;

    private final PlayerService playerService;
    private final TerritoryService territoryService;
    private final TerritoryRepository territoryRepository;
    private final DiceService diceService;

    public ActionService(PlayerService playerService, TerritoryService territoryService, TerritoryRepository territoryRepository, DiceService diceService) {
        this.playerService = playerService;
        this.territoryService = territoryService;
        this.territoryRepository = territoryRepository;
        this.diceService = diceService;
    }

    @Transactional
    public TerritoryInfo occupy(Occupation occupation, Long playerId) {
        val player = playerService.getPlayer(playerId);
        val target = territoryService.getTerritory(occupation.getTarget());

        if (target.isOccupied()) {
            throw new IllegalArgumentException("The territory '" + target.getName() + "' is occupied already.");
        }

        target.conquer(player, 1);
        territoryRepository.save(target);
        return new TerritoryInfo(target.getName(), target.getUnits());
    }

    @Transactional
    public TerritoryInfo deploy(Deployment deployment, Long playerId) {
        val player = playerService.getPlayer(playerId); // todo add validation (number of units left to place)
        val target = territoryService.getTerritory(deployment.getTarget());

        territoryService.validateOwnTerritories(player, target);

        target.addUnits(deployment.getNumberOfUnits());
        territoryRepository.save(target);
        return new TerritoryInfo(target.getName(), target.getUnits());
    }

    @Transactional
    public MovementInfo move(Movement movement, Long playerId) {
        val player = playerService.getPlayer(playerId); // todo add validation (are territories connected?)
        val source = territoryService.getTerritory(movement.getSource());
        val target = territoryService.getTerritory(movement.getTarget());

        territoryService.validateOwnTerritories(player, source, target);
        territoryService.validateRemainingUnits(source, movement.getNumberOfUnits());

        source.removeUnits(movement.getNumberOfUnits());
        target.addUnits(movement.getNumberOfUnits());
        TerritoryEntity[] updatedTerritories = {source, target};
        territoryRepository.saveAll(Arrays.asList(updatedTerritories));

        return new MovementInfo(new TerritoryInfo(source.getName(), source.getUnits()), new TerritoryInfo(target.getName(), target.getUnits()));
    }

    @Transactional
    public AttackResult attack(Movement movement, Long playerId) {
        val player = playerService.getPlayer(playerId); // todo add validation (are territories connected?)
        val source = territoryService.getTerritory(movement.getSource());
        val target = territoryService.getTerritory(movement.getTarget());

        territoryService.validateOwnTerritories(player, source);
        territoryService.validateEnemyTerritory(player, target);
        territoryService.validateRemainingUnits(source, movement.getNumberOfUnits());

        val attackDices = diceService.rollDices(Math.min(movement.getNumberOfUnits(), MAX_NUMBER_OF_ATTACK_DICES));
        val defendDices = diceService.rollDices(Math.min(target.getUnits(), MAX_NUMBER_OF_DEFEND_DICES));

        source.removeUnits(movement.getNumberOfUnits());
        int remainingAttackers = performAttacks(movement.getNumberOfUnits(), attackDices, defendDices, target);
        if (target.getUnits() == 0) {
            target.conquer(player, remainingAttackers);
        } else {
            source.addUnits(remainingAttackers);
        }

        TerritoryEntity[] updatedTerritories = {source, target};
        territoryRepository.saveAll(Arrays.asList(updatedTerritories));

        val movementInfo = new MovementInfo(new TerritoryInfo(source.getName(), source.getUnits()), new TerritoryInfo(target.getName(), target.getUnits()));
        return new AttackResult(movementInfo, attackDices, defendDices);
    }

    private int performAttacks(int attackers, List<Integer> attackDices, List<Integer> defendDices, TerritoryEntity target) {
        attackDices.sort(Collections.reverseOrder());
        defendDices.sort(Collections.reverseOrder());

        for (int i = 0; i < Math.min(attackDices.size(), defendDices.size()); i++) {
            if (attackDices.get(i).compareTo(defendDices.get(i)) > 0) {
                target.removeUnits(1);
            } else {
                attackers--;
            }
        }
        return attackers;
    }
}
