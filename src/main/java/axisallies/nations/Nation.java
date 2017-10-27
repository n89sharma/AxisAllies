package axisallies.nations;

import java.util.List;
import axisallies.units.Unit;

public class Nation {

    private NationType nationType;
    private List<String> territory;
    private List<Unit> units;

    public Nation(NationType nationType, List<String> territory, List<Unit> units) {
        this.nationType = nationType;
        this.territory = territory;
        this.units = units;
    }

    public List<String> getTerritories() {
        return territory;
    }

    public List<Unit> getUnits() {
        return units;
    }
}