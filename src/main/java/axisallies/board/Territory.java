package axisallies.board;

import java.util.Set;

import axisallies.nations.NationType;
import axisallies.units.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Territory {

    private String territoryName;
    private NationType nationType;
    private int ipc;
    private Set<String> neighbourNames;
    private Set<Unit> units;
    private Set<Territory> neighbours;

    public Territory(
        String territoryName, 
        NationType nationType, 
        int ipc) {

        this.territoryName = territoryName;
        this.nationType = nationType;
        this.ipc = ipc;
    }
}
