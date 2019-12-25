package io.scherler.games.risk.services.map;

import io.scherler.games.risk.entities.map.MapEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.map.TerritoryRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.services.ReadonlyService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TerritoryService extends ReadonlyService<TerritoryEntity> {

    private final TerritoryRepository territoryRepository;

    public TerritoryService(TerritoryRepository territoryRepository) {
        super(territoryRepository);
        this.territoryRepository = territoryRepository;
    }

    @Override
    protected String getResourceName() {
        return "Territory";
    }

    public TerritoryEntity getByName(MapEntity map, String name) {
        return territoryRepository.findByContinentMapIdAndName(map.getId(), name)
            .stream()
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Territory",
                "continent_map_id = " + map.getId() + " and name = " + name));
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

    public boolean areAdjacent(TerritoryEntity source, TerritoryEntity target) {
        return source.getAdjacentTerritories().stream()
            .anyMatch(t -> t.getId().equals(target.getId())); // todo: enable jpa caching?
    }

    public boolean areConnected(TerritoryEntity source, TerritoryEntity target,
        List<TerritoryEntity> passageTerritories) {
        if (areAdjacent(source, target)) {
            return true;
        } else {
            passageTerritories.remove(source);
            return source.getAdjacentTerritories().stream().filter(passageTerritories::contains)
                .anyMatch(nextSource -> areConnected(nextSource, target, passageTerritories));
        }
    }
}
