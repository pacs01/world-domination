package io.scherler.games.risk.services.identity;

import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.repositories.identity.UserAccountRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.request.UserAccount;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccountEntity getUser(long userId) {
        return userAccountRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("UserAccount", userId));
    }

    public UserAccountEntity createNew(UserAccount newUserAccount) {
        return userAccountRepository.save(new UserAccountEntity(newUserAccount.getName()));
    }
}
