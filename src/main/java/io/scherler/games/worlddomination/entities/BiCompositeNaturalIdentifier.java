package io.scherler.games.worlddomination.entities;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import lombok.Value;

@Value
public class BiCompositeNaturalIdentifier<F, S> implements NaturalIdentifiable {

    private HashMap<String, Object> ids = new HashMap<>();

    public BiCompositeNaturalIdentifier(SimpleEntry<String, F> firstAttribute,
        SimpleEntry<String, S> secondAttribute) {
        ids.put(firstAttribute.getKey(), firstAttribute.getValue());
        ids.put(secondAttribute.getKey(), secondAttribute.getValue());
    }
}
