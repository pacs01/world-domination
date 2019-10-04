package io.scherler.games.risk.services;

import io.scherler.games.risk.entities.OccupationEntity;
import io.scherler.games.risk.entities.repositories.OccupationRepository;
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

    private final GameService gameService;
    private final PlayerService playerService;
    private final OccupationService occupationService;
    private final TerritoryService territoryService;
    private final OccupationRepository occupationRepository;
    private final DiceService diceService;

    public ActionService(GameService gameService, PlayerService playerService, OccupationService occupationService, TerritoryService territoryService,
        OccupationRepository occupationRepository, DiceService diceService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.occupationService = occupationService;
        this.territoryService = territoryService;
        this.occupationRepository = occupationRepository;
        this.diceService = diceService;
    }

    @Transactional
    public TerritoryInfo occupy(Occupation occupation, long gameId, long playerId) {
        val game = gameService.getGame(gameId);
        val player = playerService.getPlayer(playerId);
        val target = territoryService.getTerritory(game.getMap().getId(), occupation.getTarget());

        occupationService.getOccupationIfPresent(game.getId(), target.getName()).ifPresent(o -> {
            throw new IllegalArgumentException("The territory '" + target.getName() + "' is occupied already.");
        });

        val occupationEntity = occupationService.add(game, player, target, 1);

        return new TerritoryInfo(target.getName(), occupationEntity.getUnits());
    }

    @Transactional
    public TerritoryInfo deploy(Deployment deployment, long gameId, long playerId) {
        val game = gameService.getGame(gameId);
        val player = playerService.getPlayer(playerId); // todo add validation (number of units left to place)
        val target = territoryService.getTerritory(game.getMap().getId(), deployment.getTarget());

        val occupation = occupationService.getOccupationByPlayer(game.getId(), player.getId(), target.getName());

        occupation.addUnits(deployment.getNumberOfUnits());
        occupationRepository.save(occupation);
        return new TerritoryInfo(target.getName(), occupation.getUnits());
    }

    @Transactional
    public MovementInfo move(Movement movement, long gameId, long playerId) {
        val game = gameService.getGame(gameId);
        val player = playerService.getPlayer(playerId); // todo add validation (are territories connected?)
        val source = territoryService.getTerritory(game.getMap().getId(), movement.getSource());
        val target = territoryService.getTerritory(game.getMap().getId(), movement.getTarget());

        val sourceOccupation = occupationService.getOccupationByPlayer(game.getId(), player.getId(), source.getName());
        val targetOccupation = occupationService.getOccupationByPlayer(game.getId(), player.getId(), target.getName());
        validateRemainingUnits(sourceOccupation, movement.getNumberOfUnits());

        sourceOccupation.removeUnits(movement.getNumberOfUnits());
        targetOccupation.addUnits(movement.getNumberOfUnits());
        OccupationEntity[] updatedOccupations = {sourceOccupation, targetOccupation};
        occupationRepository.saveAll(Arrays.asList(updatedOccupations));

        return new MovementInfo(new TerritoryInfo(source.getName(), sourceOccupation.getUnits()), new TerritoryInfo(target.getName(), targetOccupation.getUnits()));
    }

    @Transactional
    public AttackResult attack(Movement movement, long gameId, long playerId) {
        val game = gameService.getGame(gameId);
        val player = playerService.getPlayer(playerId); // todo add validation (are territories connected?)
        val source = territoryService.getTerritory(game.getMap().getId(), movement.getSource());
        val target = territoryService.getTerritory(game.getMap().getId(), movement.getTarget());

        val sourceOccupation = occupationService.getOccupationByPlayer(game.getId(), player.getId(), source.getName());
        val targetOccupation = occupationService.getOccupationByEnemy(game.getId(), player.getId(), target.getName());
        validateRemainingUnits(sourceOccupation, movement.getNumberOfUnits());

        val attackDices = diceService.rollDices(Math.min(movement.getNumberOfUnits(), MAX_NUMBER_OF_ATTACK_DICES));
        val defendDices = diceService.rollDices(Math.min(targetOccupation.getUnits(), MAX_NUMBER_OF_DEFEND_DICES));

        sourceOccupation.removeUnits(movement.getNumberOfUnits());
        int remainingAttackers = performAttacks(movement.getNumberOfUnits(), attackDices, defendDices, targetOccupation);
        if (targetOccupation.getUnits() == 0) {
            targetOccupation.conquer(player, remainingAttackers);
        } else {
            sourceOccupation.addUnits(remainingAttackers);
        }

        OccupationEntity[] updatedOccupations = {sourceOccupation, targetOccupation};
        occupationRepository.saveAll(Arrays.asList(updatedOccupations));

        val movementInfo = new MovementInfo(new TerritoryInfo(source.getName(), sourceOccupation.getUnits()), new TerritoryInfo(target.getName(), targetOccupation.getUnits()));
        return new AttackResult(movementInfo, attackDices, defendDices);
    }

    private int performAttacks(int attackers, List<Integer> attackDices, List<Integer> defendDices, OccupationEntity target) {
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

    private void validateRemainingUnits(OccupationEntity occupation, int numberOfUnits) {
        if (occupation.getUnits() < numberOfUnits + 1) {
            throw new IllegalArgumentException(
                "Not enough units available at territory '" + occupation.getTerritory().getName() + "'. There must remain at least one unit at every conquered place.");
        } else if (numberOfUnits < 1) {
            throw new IllegalArgumentException("An action without any units is not possible.");
        }
    }
}
