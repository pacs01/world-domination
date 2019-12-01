package io.scherler.games.risk.services.game;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.OccupationEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.game.OccupationRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.response.TerritoryInfo;
import io.scherler.games.risk.services.game.action.models.Parties;
import io.scherler.games.risk.services.game.action.models.Route;
import io.scherler.games.risk.services.map.TerritoryService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class OccupationService {

    private final OccupationRepository occupationRepository;
    private final TerritoryService territoryService;

    public OccupationService(OccupationRepository occupationRepository,
        TerritoryService territoryService) {
        this.occupationRepository = occupationRepository;
        this.territoryService = territoryService;
    }

    public OccupationEntity add(GameEntity game, PlayerEntity player, TerritoryEntity territory,
        int units) {
        val newOccupation = new OccupationEntity(game, territory, player, units);
        return occupationRepository.save(newOccupation);
    }

    public Optional<OccupationEntity> getOccupationIfPresent(long gameId, String territoryName) {
        return occupationRepository.findByGameIdAndTerritoryName(gameId, territoryName).stream()
            .findFirst();
    }

    public OccupationEntity getOccupation(long gameId, String territoryName) {
        return getOccupationIfPresent(gameId, territoryName).orElseThrow(
            () -> new ResourceNotFoundException("Occupation", "territory_name = " + territoryName));
    }

    public Optional<OccupationEntity> getOccupationByPlayerIfPresent(long gameId, long playerId,
        String territoryName) {
        return occupationRepository
            .findByGameIdAndPlayerIdAndTerritoryName(gameId, playerId, territoryName).stream()
            .findFirst();
    }

    public OccupationEntity getOccupationByPlayer(long gameId, long playerId,
        String territoryName) {
        return getOccupationByPlayerIfPresent(gameId, playerId, territoryName).orElseThrow(
            () -> new IllegalArgumentException(
                "Territory '" + territoryName + "' not occupied by player '" + playerId + "'."));
    }

    public Optional<OccupationEntity> getOccupationByEnemyIfPresent(long gameId, long playerId,
        String territoryName) {
        return occupationRepository
            .findByGameIdAndPlayerIdIsNotAndTerritoryName(gameId, playerId, territoryName).stream()
            .findFirst();
    }

    public OccupationEntity getOccupationByEnemy(long gameId, long playerId, String territoryName) {
        return getOccupationByEnemyIfPresent(gameId, playerId, territoryName).orElseThrow(
            () -> new IllegalArgumentException(
                "Territory '" + territoryName + "' not occupied by an enemy player of '" + playerId
                    + "'."));
    }

    public List<OccupationEntity> getOccupationsByPlayer(long gameId, long playerId) {
        return occupationRepository.findByGameIdAndPlayerId(gameId, playerId);
    }

    public OccupationEntity addUnits(OccupationEntity occupation, int units) {
        occupation.addUnits(units);
        return occupationRepository.save(occupation);
    }

    public Route moveUnits(Route route, int units) {
        route.getSource().removeUnits(units);
        route.getTarget().addUnits(units);
        return saveRoute(route);
    }

    public Route applyAttackResult(Route route, Parties parties, PlayerEntity attacker) {
        route.getSource().removeUnits(parties.getAttackers().getLostUnits());
        route.getTarget().removeUnits(parties.getDefenders().getLostUnits());
        if (parties.getDefenders().getRemainingUnits() == 0) {
            route.getSource().removeUnits(parties.getAttackers().getRemainingUnits());
            route.getTarget().conquer(attacker, parties.getAttackers().getRemainingUnits());
        }

        return saveRoute(route);
    }

    private Route saveRoute(Route route) {
        return Route.fromList(occupationRepository.saveAll(route.asList()), route);
    }

    public List<TerritoryInfo> getTerritoryInfos(long gameId) {
        return occupationRepository.findByGameId(gameId).stream().map(this::createTerritoryInfo)
            .collect(Collectors.toList());
    }

    private TerritoryInfo createTerritoryInfo(OccupationEntity occupation) {
        return new TerritoryInfo(occupation.getTerritory().getName(),
            occupation.getPlayer().getColor().toString(), occupation.getUnits());
    }

    public boolean areConnected(long gameId, OccupationEntity source, OccupationEntity target) {
        val occupiedTerritories = getOccupationsByPlayer(gameId, source.getPlayer().getId())
            .stream().map(OccupationEntity::getTerritory).collect(Collectors.toList());
        return territoryService
            .areConnected(source.getTerritory(), target.getTerritory(), occupiedTerritories);
    }
}
