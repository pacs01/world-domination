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
import io.scherler.games.risk.services.map.MapService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OccupationService {

    private final OccupationRepository occupationRepository;
    private final MapService mapService;

    public OccupationService(OccupationRepository occupationRepository, MapService mapService) {
        this.occupationRepository = occupationRepository;
        this.mapService = mapService;
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
        return occupationRepository.findByGameId(gameId).stream().map(this::createTerritoryInfo).collect(Collectors.toList());
    }

    private TerritoryInfo createTerritoryInfo(OccupationEntity occupation) {
        return new TerritoryInfo(occupation.getTerritory().getName(), occupation.getPlayer().getColor().toString(), occupation.getUnits());
    }

    public void validateRemainingUnits(OccupationEntity occupation, int numberOfUnits) { // todo: move out of service class -> these are strategy specific rules
        if (occupation.getUnits() < numberOfUnits + 1) {
            throw new IllegalArgumentException(
                    "Not enough units available at territory '" + occupation.getTerritory().getName() + "'. There must remain at least one unit at every conquered place.");
        } else if (numberOfUnits < 1) {
            throw new IllegalArgumentException("An action without any units is not possible.");
        }
    }
}
