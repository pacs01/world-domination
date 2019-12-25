package io.scherler.games.risk.services.map;

import io.scherler.games.risk.entities.map.ContinentEntity;
import io.scherler.games.risk.entities.map.MapEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.map.MapRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.request.identity.UserRequest;
import io.scherler.games.risk.models.request.map.Continent;
import io.scherler.games.risk.models.request.map.Map;
import io.scherler.games.risk.services.CrudService;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class MapService extends CrudService<MapEntity, UserRequest<Map>> {

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
    public MapEntity create(UserRequest<Map> request) {
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
        continent.getTerritories().forEach(t -> new TerritoryEntity(t.getName(), map, continentEntity));
        return continentEntity;
    }

    public long count() {
        return mapRepository.count();
    }
}
