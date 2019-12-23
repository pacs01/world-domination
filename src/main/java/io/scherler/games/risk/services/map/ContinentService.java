package io.scherler.games.risk.services.map;

import io.scherler.games.risk.entities.map.ContinentEntity;
import io.scherler.games.risk.entities.repositories.map.ContinentRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.services.ReadonlyService;
import org.springframework.stereotype.Service;

@Service
public class ContinentService extends ReadonlyService<ContinentEntity> {

    private final ContinentRepository continentRepository;

    public ContinentService(ContinentRepository continentRepository) {
        super(continentRepository);
        this.continentRepository = continentRepository;
    }

    @Override
    protected String getResourceName() {
        return "Continent";
    }

    public ContinentEntity getByName(long mapId, String name) {
        return continentRepository.findByMapIdAndName(mapId, name)
            .stream()
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Continent",
                "map_id = " + mapId + " and name = " + name));
    }
}
