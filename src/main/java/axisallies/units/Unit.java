package axisallies.units;

import axisallies.nations.NationType;

public class Unit {

    private String territory;
    private NationType nationType;
    private UnitType unitType;
    private int currentRange;

    public Unit() {

    }

    public Unit(String territory, NationType nationType, UnitType unitType) {
        this.territory = territory;
        this.nationType = nationType;
        this.unitType = unitType;
        this.currentRange = unitType.getMovementRange();
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public String getTerritory() {
        return territory;
    }

    public int getCurrentRange() {
        return currentRange;
    }
}
