package io.scherler.games.risk.services;

import io.scherler.games.risk.entities.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.TerritoryRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TerritoryService {

    private final TerritoryRepository territoryRepository;

    public TerritoryService(TerritoryRepository territoryRepository) {
        this.territoryRepository = territoryRepository;
    }

    public TerritoryEntity getTerritory(long mapId, String name) {
        return territoryRepository.findByContinentMapIdAndName(mapId, name).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Territory", "continent_map_id = " + mapId + " and name = " + name));
    }

    public long count() {
        return territoryRepository.count();
    }
}
