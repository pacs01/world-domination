package io.scherler.games.risk.services.game;

import io.scherler.games.risk.entities.map.MapEntity;
import io.scherler.games.risk.entities.repositories.map.MapRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
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

    public long count() {
        return mapRepository.count();
    }
}
