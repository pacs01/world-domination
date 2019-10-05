package io.scherler.games.risk.models.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Territory {

    @NotBlank(message = "Territory name is mandatory")
    private String name;
}