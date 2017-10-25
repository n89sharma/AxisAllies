package axisallies.nations;

import java.util.List;

import axisallies.units.Unit;

public class Nation {

    private NationType nationType;
    private List<String> regions;
    private List<Unit> units;

    public Nation(NationType nationType, List<String> regions, List<Unit> units) {
        this.nationType = nationType;
        this.regions = regions;
        this.units = units;
    }
}