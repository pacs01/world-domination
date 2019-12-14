package io.scherler.games.risk.models.request;

import io.scherler.games.risk.entities.identity.UserAccountEntity;
import lombok.Value;

@Value
public class UserRequest<T> {

    private T requestObject;

    private UserAccountEntity userAccount;
}
