package io.scherler.games.risk.services.identity;

import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.repositories.identity.UserAccountRepository;
import io.scherler.games.risk.models.request.identity.UserAccount;
import io.scherler.games.risk.services.CrudService;
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
