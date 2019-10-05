package io.scherler.games.risk.models.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAccount {

    @NotBlank(message = "Username is mandatory")
    private String name;
}
