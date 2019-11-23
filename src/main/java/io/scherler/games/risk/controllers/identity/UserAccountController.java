package io.scherler.games.risk.controllers.identity;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.risk.controllers.defaults.DefaultResourceAssembler;
import io.scherler.games.risk.controllers.defaults.DefaultResourceController;
import io.scherler.games.risk.entities.identity.UserAccountEntity;
import io.scherler.games.risk.entities.repositories.identity.UserAccountRepository;
import io.scherler.games.risk.exceptions.ResourceNotFoundException;
import io.scherler.games.risk.models.request.UserAccount;
import io.scherler.games.risk.services.identity.UserAccountService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserAccountController implements DefaultResourceController<UserAccountEntity> {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountService userAccountService;
    private final DefaultResourceAssembler<UserAccountEntity> defaultResourceAssembler;

    public UserAccountController(UserAccountRepository userAccountRepository, UserAccountService userAccountService) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountService = userAccountService;
        this.defaultResourceAssembler = new DefaultResourceAssembler<>(this);
    }

    @GetMapping()
    public Resources<Resource<UserAccountEntity>> getAll() {
        List<Resource<UserAccountEntity>> userAccounts = userAccountRepository.findAll().stream().map(defaultResourceAssembler::toResource).collect(Collectors.toList());

        return new Resources<>(userAccounts, linkTo(methodOn(UserAccountController.class).getAll()).withSelfRel());
    }

    @PostMapping()
    public ResponseEntity<?> createNew(@Valid @RequestBody UserAccount newUserAccount) throws URISyntaxException {
        Resource<UserAccountEntity> resource = defaultResourceAssembler.toResource(userAccountService.createNew(newUserAccount));

        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping("/{id}")
    public Resource<UserAccountEntity> getOne(@PathVariable Long id) {
        return defaultResourceAssembler.toResource(userAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UserAccount", id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userAccountRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
