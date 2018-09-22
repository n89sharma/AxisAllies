package axisallies.units;

import axisallies.board.Territory;
import axisallies.nations.NationType;

import java.util.HashSet;
import java.util.Set;

public class CarrierUnit extends Unit {

    private Set<Unit> units = new HashSet<>();

    public CarrierUnit(UnitType unitType, NationType nationType) {
        super(unitType, nationType);
    }

    public CarrierUnit(UnitType unitType, NationType nationType, Territory territory) {
        super(unitType, nationType, territory);
    }

    public boolean containsUnits() {
        return !units.isEmpty();
    }

    public void addContainedUnit(Unit unit) {
        units.add(unit);
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }
}