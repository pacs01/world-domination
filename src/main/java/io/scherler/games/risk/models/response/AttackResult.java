package io.scherler.games.risk.models.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttackResult {

    private MovementInfo movementInfo;

    private List<Integer> attackDices;

    private List<Integer> defendDices;
}
