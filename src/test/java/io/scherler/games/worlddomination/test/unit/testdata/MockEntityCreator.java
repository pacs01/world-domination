package io.scherler.games.worlddomination.test.unit.testdata;

import io.scherler.games.worlddomination.entities.BaseEntity;

class MockEntityCreator<T extends BaseEntity> {

    private long idCounter = 1;

    T create(T entity) {
        entity.setId(idCounter++);
        return entity;
    }
}
