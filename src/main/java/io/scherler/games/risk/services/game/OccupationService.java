package io.scherler.games.risk.services.game;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.OccupationEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.game.OccupationRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import java.util.Optional;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class OccupationService {

    private final OccupationRepository occupationRepository;

    public OccupationService(OccupationRepository occupationRepository) {
        this.occupationRepository = occupationRepository;
    }

    public OccupationEntity add(GameEntity game, PlayerEntity player, TerritoryEntity territory, int units) {
        val newOccupation = new OccupationEntity(game, player, territory, units);
        return occupationRepository.save(newOccupation);
    }

    public Optional<OccupationEntity> getOccupationIfPresent(long gameId, String territoryName) {
        return occupationRepository.findByGameIdAndTerritoryName(gameId, territoryName).stream().findFirst();
    }

    public OccupationEntity getOccupation(long gameId, String territoryName) {
        return getOccupationIfPresent(gameId, territoryName).orElseThrow(() -> new ResourceNotFoundException("Occupation", "territory_name = " + territoryName));
    }

    public Optional<OccupationEntity> getOccupationByPlayerIfPresent(long gameId, long playerId, String territoryName) {
        return occupationRepository.findByGameIdAndPlayerIdAndTerritoryName(gameId, playerId, territoryName).stream().findFirst();
    }

    public OccupationEntity getOccupationByPlayer(long gameId, long playerId, String territoryName) {
        return getOccupationByPlayerIfPresent(gameId, playerId, territoryName).orElseThrow(
            () -> new IllegalArgumentException("Territory '" + territoryName + "' not occupied by player '" + playerId + "'."));
    }

    public Optional<OccupationEntity> getOccupationByEnemyIfPresent(long gameId, long playerId, String territoryName) {
        return occupationRepository.findByGameIdAndPlayerIdIsNotAndTerritoryName(gameId, playerId, territoryName).stream().findFirst();
    }

    public OccupationEntity getOccupationByEnemy(long gameId, long playerId, String territoryName) {
        return getOccupationByEnemyIfPresent(gameId, playerId, territoryName).orElseThrow(
            () -> new IllegalArgumentException("Territory '" + territoryName + "' not occupied by an enemy player of '" + playerId + "'."));
    }
}
