package io.scherler.games.risk.services;

import io.scherler.games.risk.controllers.ResourceNotFoundException;
import io.scherler.games.risk.entities.PlayerEntity;
import io.scherler.games.risk.entities.TerritoryEntity;
import io.scherler.games.risk.entities.TerritoryRepository;
import java.util.Arrays;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class TerritoryService {

    private final TerritoryRepository territoryRepository;

    public TerritoryService(TerritoryRepository territoryRepository) {
        this.territoryRepository = territoryRepository;
    }

    public TerritoryEntity getTerritory(String name) {
        return territoryRepository.findByName(name).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Territory", "name = " + name));
    }

    public void validateOwnTerritories(PlayerEntity player, TerritoryEntity... territories) {
        val foreignTerritory = Arrays.stream(territories).filter(t -> !t.isOccupiedBy(player)).findAny();
        foreignTerritory.ifPresent(t -> {
            throw new IllegalArgumentException("Territory '" + t.getName() + "' not occupied by player '" + player.getColor() + "'.");
        });
    }

    public void validateEnemyTerritory(PlayerEntity player, TerritoryEntity territory) {
        if (!territory.isOccupied() || territory.isOccupiedBy(player)) {
            throw new IllegalArgumentException("Territory '" + territory.getName() + "' not occupied by an enemy player.");
        }
    }

    public void validateRemainingUnits(TerritoryEntity territory, int numberOfUnits) {
        if (territory.getUnits() < numberOfUnits + 1) {
            throw new IllegalArgumentException(
                "Not enough units available at territory '" + territory.getName() + "'. There must remain at least one unit at every conquered place.");
        } else if (numberOfUnits < 1) {
            throw new IllegalArgumentException("An action without any units is not possible.");
        }
    }
}
