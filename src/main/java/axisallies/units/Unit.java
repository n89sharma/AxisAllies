package axisallies.units;

import axisallies.nations.NationType;

public class Unit {

    private String territory;
    private NationType nationType;
    private UnitType unitType;

    public Unit() {

    }

    public Unit(String territory, NationType nationType, UnitType unitType) {
        this.territory = territory;
        this.nationType = nationType;
        this.unitType = unitType;
    }

    public UnitType getUnitType() {
        return unitType;
    }
}
