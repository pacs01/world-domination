package io.scherler.games.risk.services;

import io.scherler.games.risk.controllers.ResourceNotFoundException;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.entities.TerritoryEntity;
import io.scherler.games.risk.entities.TerritoryRepository;
import io.scherler.games.risk.entities.UnitEntity;
import io.scherler.games.risk.entities.UnitRepository;
import io.scherler.games.risk.models.Deployment;
import io.scherler.games.risk.models.DeploymentResult;
import io.scherler.games.risk.models.Movement;
import io.scherler.games.risk.models.MovementResult;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class UnitService {

    private final UnitRepository unitRepository;
    private final PlayerService playerService;
    private final TerritoryRepository territoryRepository;

    public UnitService(UnitRepository unitRepository, PlayerService playerService, TerritoryRepository territoryRepository) {
        this.unitRepository = unitRepository;
        this.playerService = playerService;
        this.territoryRepository = territoryRepository;
    }

    public DeploymentResult deploy(Deployment deployment, Long playerId) {
        val player = playerService.getPlayer(playerId); // todo add validation (number of units left && own or free territory?)
        val territory = getTerritory(deployment.getTarget());

        createUnits(deployment.getNumberOfUnits(), player, territory);

        return new DeploymentResult(territory.getName(), territory.getUnitEntities().stream().filter(u -> u.getPlayer().getId().equals(playerId)).count());
    }

    public MovementResult move(Movement movement, Long playerId) {
        val player = playerService.getPlayer(playerId); // todo add validation (own or free territory?)
        val source = getTerritory(movement.getSource());
        val target = getTerritory(movement.getTarget());

        val units = getUnits(movement.getNumberOfUnits(), player, source);

        val movedUnits = units.stream().peek(u -> u.setTerritory(target)).collect(Collectors.toList());
        unitRepository.saveAll(movedUnits);

        return new MovementResult(source.getName(), source.getUnitEntities().size(), target.getName(), target.getUnitEntities().size());
    }

    private TerritoryEntity getTerritory(String name) {
        return territoryRepository.findByName(name).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException(name));
    }

    private List<UnitEntity> createUnits(int numberOfUnits, PlayerEntity player, TerritoryEntity territory) {
        List<UnitEntity> units = new ArrayList<>();
        for (int i = 0; i < numberOfUnits; i++) {
            units.add(new UnitEntity(player, territory));
        }
        return unitRepository.saveAll(units);
    }

    private List<UnitEntity> getUnits(int numberOfUnits, PlayerEntity owner, TerritoryEntity territory) {
        List<UnitEntity> units = territory.getUnitEntities().stream().filter(u -> u.getPlayer().getId().equals(owner.getId())).limit(numberOfUnits).collect(Collectors.toList());

        if (units.size() < numberOfUnits) {
            throw new IllegalArgumentException("Not enough units available at territory '" + territory.getName() + "'.");
        }
        return units;
    }
}
