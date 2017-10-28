package axisallies.nations;

import java.util.List;
import axisallies.board.Territory;
import axisallies.units.Unit;

public class Nation {

    private NationType nationType;
    private List<Territory> territory;
    private List<Unit> units;

    public Nation(NationType nationType, List<Territory> territory, List<Unit> units) {
        this.nationType = nationType;
        this.territory = territory;
        this.units = units;
    }

    public List<Territory> getTerritories() {
        return territory;
    }

    public List<Unit> getUnits() {
        return units;
    }
}