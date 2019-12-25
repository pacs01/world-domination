package io.scherler.games.worlddomination.services.map;

import io.scherler.games.worlddomination.entities.map.ContinentEntity;
import io.scherler.games.worlddomination.entities.map.MapEntity;
import io.scherler.games.worlddomination.entities.map.TerritoryEntity;
import io.scherler.games.worlddomination.entities.repositories.map.MapRepository;
import io.scherler.games.worlddomination.exceptions.ResourceNotFoundException;
import io.scherler.games.worlddomination.models.request.identity.UserRequest;
import io.scherler.games.worlddomination.models.request.map.Continent;
import io.scherler.games.worlddomination.models.request.map.WorldMap;
import io.scherler.games.worlddomination.services.CrudService;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class MapService extends CrudService<MapEntity, UserRequest<WorldMap>> {

    private final MapRepository mapRepository;

    public MapService(MapRepository mapRepository) {
        super(mapRepository);
        this.mapRepository = mapRepository;
    }

    @Override
    protected String getResourceName() {
        return "Map";
    }

    @Override
    public MapEntity create(UserRequest<WorldMap> request) {
        val map = request.getRequestObject();
        val newMap = new MapEntity(map.getName(), request.getUserAccount());
        map.getContinents().forEach(c -> createContinent(newMap, c));
        return mapRepository.save(newMap);
    }

    public MapEntity getMap(String name) {
        return mapRepository.findBySimpleNaturalId(name).stream().findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Map", "name = " + name));
    }

    private ContinentEntity createContinent(MapEntity map, Continent continent) {
        val continentEntity = new ContinentEntity(map, continent.getName());
        continent.getTerritories()
            .forEach(t -> new TerritoryEntity(t.getName(), map, continentEntity));
        return continentEntity;
    }

    public long count() {
        return mapRepository.count();
    }
}
