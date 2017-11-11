package axisallies.board;

import axisallies.nations.NationType;
import axisallies.units.Unit;
import lombok.*;

import java.util.Set;

import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;

@Setter
@Getter
@ToString(exclude = {"units", "neighbours"})
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"units", "neighbours"})
public class Territory {

    private String territoryName;
    private NationType nationType;
    private int ipc;
    private Set<String> neighbourNames;
    private Set<Unit> units;
    private TerritoryType territoryType;
    private Set<Territory> neighbours;

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public boolean isSea() {
        return territoryType.equals(SEA);
    }

    public boolean isLand() {
        return territoryType.equals(LAND);
    }

}
