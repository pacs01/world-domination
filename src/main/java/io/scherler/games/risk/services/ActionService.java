package io.scherler.games.risk.services;

import io.scherler.games.risk.controllers.ResourceNotFoundException;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.entities.TerritoryEntity;
import io.scherler.games.risk.entities.TerritoryRepository;
import io.scherler.games.risk.models.Deployment;
import io.scherler.games.risk.models.DeploymentResult;
import io.scherler.games.risk.models.Movement;
import io.scherler.games.risk.models.MovementResult;
import io.scherler.games.risk.models.Occupation;
import java.util.Arrays;
import javax.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

    private final PlayerService playerService;
    private final TerritoryRepository territoryRepository;

    public ActionService(PlayerService playerService, TerritoryRepository territoryRepository) {
        this.playerService = playerService;
        this.territoryRepository = territoryRepository;
    }

    @Transactional
    public void occupy(Occupation occupation, Long playerId) {
        val player = playerService.getPlayer(playerId);
        val territory = getTerritory(occupation.getTarget());

        if (territory.getPlayer() != null) {
            throw new IllegalArgumentException("The territory '" + territory.getName() + "' is occupied already.");
        } else {
            territory.addUnits(1);
            territory.setPlayer(player);
        }
    }

    @Transactional
    public DeploymentResult deploy(Deployment deployment, Long playerId) {
        val player = playerService.getPlayer(playerId); // todo add validation (number of units left to place)
        val target = getTerritory(deployment.getTarget());

        validateOwnership(player, target);
        target.addUnits(deployment.getNumberOfUnits());

        territoryRepository.save(target);

        return new DeploymentResult(target.getName(), target.getUnits());
    }

    @Transactional
    public MovementResult move(Movement movement, Long playerId) {
        val player = playerService.getPlayer(playerId);
        val source = getTerritory(movement.getSource());
        val target = getTerritory(movement.getTarget());

        validateOwnership(player, source, target);
        validateRemainingUnits(source, movement.getNumberOfUnits());

        source.removeUnits(movement.getNumberOfUnits());
        target.addUnits(movement.getNumberOfUnits());

        TerritoryEntity[] updatedTerritories = {source, target};
        territoryRepository.saveAll(Arrays.asList(updatedTerritories));

        return new MovementResult(source.getName(), source.getUnits(), target.getName(), target.getUnits());
    }

    private TerritoryEntity getTerritory(String name) {
        return territoryRepository.findByName(name).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException(name));
    }

    private void validateOwnership(PlayerEntity playerEntity, TerritoryEntity... territories) {
        val enemyTerritory = Arrays.stream(territories).filter(t -> t.getPlayer() == null || !t.getPlayer().getId().equals(playerEntity.getId())).findAny();
        enemyTerritory.ifPresent(t -> {
            throw new IllegalArgumentException("Territory '" + t.getName() + "' not occupied by this player.");
        });
    }

    private void validateRemainingUnits(TerritoryEntity territory, int numberOfUnits) {
        if (territory.getUnits() < numberOfUnits + 1) {
            throw new IllegalArgumentException("Not enough units available at territory '" + territory.getName() + "'. There must remain at least one unit at every conquered place.");
        }
    }
}
