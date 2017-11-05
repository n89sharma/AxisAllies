package axisallies.board;

import axisallies.nations.NationType;
import axisallies.units.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;

@Data
@AllArgsConstructor
public class Territory {

    private String territoryName;
    private NationType nationType;
    private int ipc;
    private Set<String> neighbourNames;
    private Set<Unit> units;
    private TerritoryType territoryType;

    public boolean isSea() {
        return territoryType.equals(SEA);
    }

    public boolean isLand() {
        return territoryType.equals(LAND);
    }

    public boolean isHostileTo(Unit unit) {
        return nationType.getTeamType().isHostileTo(unit.getNationType().getTeamType());
    }

    public boolean isFriendlyTo(Unit unit) {
        return !isHostileTo(unit);
    }
}
