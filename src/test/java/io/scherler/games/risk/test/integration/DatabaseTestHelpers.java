package io.scherler.games.risk.test.integration;

import io.scherler.games.risk.entities.game.GameEntity;
import io.scherler.games.risk.entities.game.PlayerEntity;
import io.scherler.games.risk.models.request.identity.UserAccount;
import io.scherler.games.risk.models.request.identity.UserRequest;
import io.scherler.games.risk.models.response.IdentifiableResource;
import io.scherler.games.risk.services.game.PlayerService;
import io.scherler.games.risk.services.identity.UserAccountService;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Service;

@Service
public class DatabaseTestHelpers {

    private final UserAccountService userAccountService;
    private final PlayerService playerService;
    private static final String PACKAGE_DOMAIN;

    static {
        val packages = DatabaseTestHelpers.class.getName().split("\\.", 3);
        PACKAGE_DOMAIN = packages[0] + "." + packages[1];
    }

    public DatabaseTestHelpers(
        UserAccountService userAccountService,
        PlayerService playerService) {
        this.userAccountService = userAccountService;
        this.playerService = playerService;
    }

    List<PlayerEntity> generatePlayers(GameEntity game, int number) {
        val playerList = new ArrayList<PlayerEntity>();
        for (int i = 0; i < number; i++) {
            val userAccount = userAccountService
                .create(new UserAccount(game.getName() + "-user-" + i));
            playerList.add(playerService.create(new UserRequest<>(game, userAccount)));
        }

        return playerList;
    }

    /**
     * Helper method to compare mocked data objects from type
     * {@link io.scherler.games.risk.models.response.IdentifiableResource}
     * or that have (nested) fields from this type to real data objects that are filled with
     * database ids. Warning: id fields of param expected are updated!
     *
     * @param expected mocked data object without (correct) ids
     * @param actual   actual data object containing the correct ids
     * @param <T>      any type of data class that is an
     * {@link io.scherler.games.risk.models.response.IdentifiableResource}
     *                 or has (nested) fields from this type
     */
    static <T> void assertEqualsExcludeIdFields(T expected, T actual) {
        DatabaseTestHelpers.setIds(expected, actual);
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Helper method needed to set id fields of mocked data objects (unidentified) to be compared to
     * real data objects (identified) filled with database ids.
     *
     * @param unidentified data object witch id fields should be (over)written
     * @param identified   data object containing the correct ids
     * @param <T>          any type of data class that is an
     * {@link io.scherler.games.risk.models.response.IdentifiableResource}
     *                     or has (nested) fields from this type
     */
    private static <T> void setIds(T unidentified, T identified) {
        if (IdentifiableResource.class.isAssignableFrom(unidentified.getClass())) {
            ((IdentifiableResource) unidentified)
                .setId(((IdentifiableResource) identified).getId());
        }
        for (Field declaredField : unidentified.getClass().getDeclaredFields()) {
            try {
                if (declaredField.getType().getName().startsWith(PACKAGE_DOMAIN)) {
                    declaredField.setAccessible(true);
                    setIds(declaredField.get(unidentified), declaredField.get(identified));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
