package io.scherler.games.worlddomination.models.request.identity;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

    @NotBlank(message = "Username is mandatory")
    private String name;
}
