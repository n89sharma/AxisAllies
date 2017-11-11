package axisallies.units;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true, exclude="units")
public class CarrierUnit extends Unit {
    
    private Set<Unit> units = new HashSet<>();

    public CarrierUnit(UnitType unitType) {
        super(unitType);
    }
}