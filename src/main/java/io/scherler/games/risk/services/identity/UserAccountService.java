package io.scherler.games.risk.services.identity;

import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.repositories.identity.UserAccountRepository;
import io.scherler.games.risk.models.request.UserAccount;
import io.scherler.games.risk.services.CrudService;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService extends CrudService<UserAccountEntity, UserAccount> {

    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        super(userAccountRepository);
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    protected String getResourceName() {
        return "UserAccount";
    }

    @Override
    public UserAccountEntity create(UserAccount request) {
        return userAccountRepository.save(new UserAccountEntity(request.getName()));
    }
}
