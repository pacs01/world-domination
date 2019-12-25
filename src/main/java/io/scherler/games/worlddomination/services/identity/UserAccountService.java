package io.scherler.games.worlddomination.services.identity;

import io.scherler.games.worlddomination.entities.identity.UserAccountEntity;
import io.scherler.games.worlddomination.entities.repositories.identity.UserAccountRepository;
import io.scherler.games.worlddomination.models.request.identity.UserAccount;
import io.scherler.games.worlddomination.services.CrudService;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService extends CrudService<UserAccountEntity, UserAccount> {

    public UserAccountService(UserAccountRepository userAccountRepository) {
        super(userAccountRepository);
    }

    @Override
    protected String getResourceName() {
        return "UserAccount";
    }

    @Override
    public UserAccountEntity create(UserAccount request) {
        return entityRepository.save(new UserAccountEntity(request.getName()));
    }
}
