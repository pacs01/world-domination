package io.scherler.games.risk.test.unit.testdata;

import io.scherler.games.risk.entities.BaseEntity;

class MockEntityCreator<T extends BaseEntity> {

    private long idCounter = 1;

    T create(T entity) {
        entity.setId(idCounter++);
        return entity;
    }
}
