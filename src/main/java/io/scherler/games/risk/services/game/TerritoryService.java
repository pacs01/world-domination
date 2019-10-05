package io.scherler.games.risk.services.game;

import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.map.TerritoryRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TerritoryService {

    private final TerritoryRepository territoryRepository;

    public TerritoryService(TerritoryRepository territoryRepository) {
        this.territoryRepository = territoryRepository;
    }

    public TerritoryEntity getTerritory(long mapId, String name) {
        return territoryRepository.findByContinentMapIdAndName(mapId, name)
                                  .stream()
                                  .findFirst()
                                  .orElseThrow(() -> new ResourceNotFoundException("Territory", "continent_map_id = " + mapId + " and name = " + name));
    }

    public List<TerritoryEntity> getRemainingTerritories(long mapId, List<Long> territoryIds) {
        if (territoryIds.isEmpty()) {
            return territoryRepository.findByContinentMapId(mapId);
        } else {
            return territoryRepository.findByContinentMapIdAndIdNotIn(mapId, territoryIds);
        }
    }

    public long count(long mapId) {
        return territoryRepository.countByContinentMapId(mapId);
    }
}
