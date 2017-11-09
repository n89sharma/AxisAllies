package axisallies.units;

import static axisallies.units.UnitType.AIRCRAFT_CARRIER;
import static axisallies.units.UnitType.TRANSPORT;
import axisallies.units.UnitType;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnitFactory {
    public static Unit buildUnit(UnitType unitType) {

        return (unitType.equals(AIRCRAFT_CARRIER) || unitType.equals(TRANSPORT)) ?
            new CarrierUnit(unitType) :
            new Unit(unitType);
    }
}