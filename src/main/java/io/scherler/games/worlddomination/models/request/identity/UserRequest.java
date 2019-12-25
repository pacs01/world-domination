package io.scherler.games.worlddomination.models.request.identity;

import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import lombok.Value;

@Value
public class UserRequest<T> {

    private T requestObject;

    private UserAccountEntity userAccount;
}
