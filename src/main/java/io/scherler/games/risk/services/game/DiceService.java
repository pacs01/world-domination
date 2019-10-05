package io.scherler.games.risk.services.game;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class DiceService {

    public List<Integer> rollDices(int numberOfDices) {
        return IntStream.generate(() -> ThreadLocalRandom.current().nextInt(6) + 1).limit(numberOfDices).boxed().collect(Collectors.toList());
    }
}
