package axisallies.nations;

import java.util.Set;
import axisallies.board.Territory;
import axisallies.units.Unit;

public class Nation {

    private NationType nationType;
    private Set<Territory> territory;
    private Set<Unit> units;

    public Nation(NationType nationType, Set<Territory> territory, Set<Unit> units) {
        this.nationType = nationType;
        this.territory = territory;
        this.units = units;
    }

    public Set<Territory> getTerritories() {
        return territory;
    }

    public Set<Unit> getUnits() {
        return units;
    }
}