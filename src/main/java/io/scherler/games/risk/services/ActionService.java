package io.scherler.games.risk.services;

import io.scherler.games.risk.controllers.ResourceNotFoundException;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.entities.TerritoryEntity;
import io.scherler.games.risk.entities.TerritoryRepository;
import io.scherler.games.risk.models.AttackResult;
import io.scherler.games.risk.models.Deployment;
import io.scherler.games.risk.models.Movement;
import io.scherler.games.risk.models.MovementInfo;
import io.scherler.games.risk.models.Occupation;
import io.scherler.games.risk.models.TerritoryInfo;
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
    private final TerritoryRepository territoryRepository;
    private final DiceService diceService;

    public ActionService(PlayerService playerService, TerritoryRepository territoryRepository, DiceService diceService) {
        this.playerService = playerService;
        this.territoryRepository = territoryRepository;
        this.diceService = diceService;
    }

    @Transactional
    public void occupy(Occupation occupation, Long playerId) {
        val player = playerService.getPlayer(playerId);
        val target = getTerritory(occupation.getTarget());

        if (target.isOccupied()) {
            throw new IllegalArgumentException("The territory '" + target.getName() + "' is occupied already.");
        }

        target.conquer(player, 1);
        territoryRepository.save(target);
    }

    @Transactional
    public TerritoryInfo deploy(Deployment deployment, Long playerId) {
        val player = playerService.getPlayer(playerId); // todo add validation (number of units left to place)
        val target = getTerritory(deployment.getTarget());

        validateOwnTerritories(player, target);

        target.addUnits(deployment.getNumberOfUnits());
        territoryRepository.save(target);
        return new TerritoryInfo(target.getName(), target.getUnits());
    }

    @Transactional
    public MovementInfo move(Movement movement, Long playerId) {
        val player = playerService.getPlayer(playerId); // todo add validation (are territories connected?)
        val source = getTerritory(movement.getSource());
        val target = getTerritory(movement.getTarget());

        validateOwnTerritories(player, source, target);
        validateRemainingUnits(source, movement.getNumberOfUnits());

        source.removeUnits(movement.getNumberOfUnits());
        target.addUnits(movement.getNumberOfUnits());
        TerritoryEntity[] updatedTerritories = {source, target};
        territoryRepository.saveAll(Arrays.asList(updatedTerritories));

        return new MovementInfo(new TerritoryInfo(source.getName(), source.getUnits()), new TerritoryInfo(target.getName(), target.getUnits()));
    }

    @Transactional
    public AttackResult attack(Movement movement, Long playerId) {
        val player = playerService.getPlayer(playerId); // todo add validation (are territories connected?)
        val source = getTerritory(movement.getSource());
        val target = getTerritory(movement.getTarget());

        validateOwnTerritories(player, source);
        validateEnemyTerritory(player, target);
        validateRemainingUnits(source, movement.getNumberOfUnits());

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

    private TerritoryEntity getTerritory(String name) {
        return territoryRepository.findByName(name).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException(name));
    }

    private void validateOwnTerritories(PlayerEntity player, TerritoryEntity... territories) {
        val foreignTerritory = Arrays.stream(territories).filter(t -> !t.isOccupiedBy(player)).findAny();
        foreignTerritory.ifPresent(t -> {
            throw new IllegalArgumentException("Territory '" + t.getName() + "' not occupied by player '" + player.getColor() + "'.");
        });
    }

    private void validateEnemyTerritory(PlayerEntity player, TerritoryEntity territory) {
        if (!territory.isOccupied() || territory.isOccupiedBy(player)) {
            throw new IllegalArgumentException("Territory '" + territory.getName() + "' not occupied by an enemy player.");
        }
    }

    private void validateRemainingUnits(TerritoryEntity territory, int numberOfUnits) {
        if (territory.getUnits() < numberOfUnits + 1) {
            throw new IllegalArgumentException(
                "Not enough units available at territory '" + territory.getName() + "'. There must remain at least one unit at every conquered place.");
        } else if (numberOfUnits < 1) {
            throw new IllegalArgumentException("An action without any units is not possible.");
        }
    }
}
