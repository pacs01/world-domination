package io.scherler.games.risk.services.game;

import io.scherler.games.risk.entities.game.CardEntity;
import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.entities.map.TerritoryEntity;
import io.scherler.games.risk.entities.repositories.game.CardRepository;
import io.scherler.games.risk.models.response.Card;
import io.scherler.games.risk.services.map.TerritoryService;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    public static final String VALAR_MORGHULIS = "Valar Morghulis";

    private final CardRepository cardRepository;

    private final TerritoryService territoryService;

    public CardService(CardRepository cardRepository, TerritoryService territoryService) {
        this.cardRepository = cardRepository;
        this.territoryService = territoryService;
    }

    public List<CardEntity> getAllCards(long gameId) {
        return cardRepository.findByGameId(gameId);
    }

    public List<CardEntity> getCardsByPlayer(long gameId, long playerId) {
        return cardRepository.findByGameIdAndPlayerId(gameId, playerId);
    }

    public Card drawNextCard(GameEntity game, PlayerEntity player) {
        val cards = getAllCards(game.getId());
        val remainingTerritories = territoryService.getRemainingTerritories(game.getMap().getId(),
            cards.stream().map(c -> c.getTerritory().getId()).collect(Collectors.toList()));

        val numberOfTerritories = territoryService.count(game.getMap().getId());
        int numberOfCards = remainingTerritories.size();
        if (remainingTerritories.size() < numberOfTerritories / 2) {
            numberOfCards++;
        }

        Random r = new Random();
        val pickedIndex = r.nextInt(numberOfCards);
        if (pickedIndex == remainingTerritories.size()) {
            return new Card(VALAR_MORGHULIS);
        }

        return drawCard(game, player, remainingTerritories.get(pickedIndex));
    }

    private Card drawCard(GameEntity game, PlayerEntity player, TerritoryEntity territory) {
        val card = new CardEntity(game, territory, player);
        cardRepository.save(card);
        return new Card(territory.getName());
    }
}
