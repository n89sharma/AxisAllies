package axisallies.units;

import java.util.HashSet;
import java.util.Set;
import axisallies.board.Territory;
import axisallies.nations.NationType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true, exclude="units")
public class CarrierUnit extends Unit {
    
    private Set<Unit> units = new HashSet<>();

    public CarrierUnit(UnitType unitType) {
        super(unitType);
    }

    public CarrierUnit(UnitType unitType, NationType nationType) {
        super(unitType, nationType);
    }

    public CarrierUnit(UnitType unitType, NationType nationType, Territory territory) {
        super(unitType, nationType, territory);
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }
}