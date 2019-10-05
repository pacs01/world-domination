package io.scherler.games.risk.services.map;

import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.map.ContinentEntity;
import io.scherler.games.risk.entities.map.MapEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.map.MapRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.request.Continent;
import io.scherler.games.risk.models.request.Map;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    private final MapRepository mapRepository;

    public MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    public MapEntity getMap(String name) {
        return mapRepository.findByName(name).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Map", "name = " + name));
    }

    public MapEntity createNew(Map newMap, UserAccountEntity user) {
        val map = new MapEntity(newMap.getName(), user);
        val continents = newMap.getContinents().stream().map(c -> createContinent(map, c)).collect(Collectors.toSet());
        map.setContinents(continents);
        return mapRepository.save(map);
    }

    private ContinentEntity createContinent(MapEntity map, Continent continent) {
        val continentEntity = new ContinentEntity(map, continent.getName());
        val territories = continent.getTerritories().stream().map(t -> new TerritoryEntity(t.getName(), continentEntity)).collect(Collectors.toSet());
        continentEntity.setTerritoryEntities(territories);
        return continentEntity;
    }

    public long count() {
        return mapRepository.count();
    }
}
