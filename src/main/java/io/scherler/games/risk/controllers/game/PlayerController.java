package io.scherler.games.risk.controllers.game;

import io.scherler.games.risk.entities.repositories.game.PlayerRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games/{gameId}/players")
public class PlayerController {

    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable("gameId") Long gameId) {
        val players = playerRepository.findByGameId(gameId);
        return ResponseEntity.ok().body(players); //todo add hateoas
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getOne(@PathVariable("gameId") Long gameId, @PathVariable Long playerId) {
        val player = playerRepository.findByIdAndGameId(playerId, gameId).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Player", playerId));
        return ResponseEntity.ok().body(player); //todo add hateoas
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<?> delete(@PathVariable("gameId") Long gameId, @PathVariable Long playerId) {
        playerRepository.deleteById(playerId);

        return ResponseEntity.noContent().build();
    }
}
