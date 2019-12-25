package io.scherler.games.worlddomination.controllers.identity;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.scherler.games.worlddomination.controllers.defaults.DefaultResourceAssembler;
import io.scherler.games.worlddomination.controllers.defaults.DefaultResourceController;
import io.scherler.games.worlddomination.models.request.identity.UserAccount;
import io.scherler.games.worlddomination.models.response.identity.UserInfo;
import io.scherler.games.worlddomination.services.identity.UserAccountService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.val;
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
public class UserAccountController implements DefaultResourceController<UserInfo> {

    private final UserAccountService userAccountService;
    private final DefaultResourceAssembler<UserInfo> resourceAssembler;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
        this.resourceAssembler = new DefaultResourceAssembler<>(this);
    }

    @Override
    @GetMapping
    public Resources<Resource<UserInfo>> getAll() {
        List<Resource<UserInfo>> userAccounts = userAccountService.getAll().stream()
            .map(UserInfo::from).map(resourceAssembler::toResource)
            .collect(Collectors.toList());

        return new Resources<>(userAccounts,
            linkTo(methodOn(UserAccountController.class).getAll()).withSelfRel());
    }

    @Override
    @GetMapping("/{id}")
    public Resource<UserInfo> getOne(@PathVariable Long id) {
        return resourceAssembler.toResource(UserInfo.from(userAccountService.get(id)));
    }

    @PostMapping
    public ResponseEntity<?> createNew(@Valid @RequestBody UserAccount newUserAccount)
        throws URISyntaxException {
        val userAccount = userAccountService.create(newUserAccount);
        Resource<UserInfo> resource = resourceAssembler
            .toResource(UserInfo.from(userAccount));

        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userAccountService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
