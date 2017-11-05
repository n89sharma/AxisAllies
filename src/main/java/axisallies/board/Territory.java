package axisallies.board;

import axisallies.nations.NationType;
import axisallies.units.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Territory {

    private String territoryName;
    private NationType nationType;
    private int ipc;
    private Set<String> neighbourNames;
    private Set<Unit> units;
}
