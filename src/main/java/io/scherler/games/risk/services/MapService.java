package io.scherler.games.risk.services;

import io.scherler.games.risk.entities.MapEntity;
import io.scherler.games.risk.entities.repositories.MapRepository;
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
