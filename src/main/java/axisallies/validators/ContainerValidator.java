package axisallies.validators;

import static axisallies.units.UnitType.AIRCRAFT_CARRIER;
import static axisallies.units.UnitType.FIGHTER;
import static axisallies.units.UnitType.TRANSPORT;

import axisallies.units.Unit;

public class ContainerValidator {
    public static boolean isValidType(Unit carrierUnit, Unit cargoUnit) {
        return ((carrierUnit.isType(AIRCRAFT_CARRIER) && cargoUnit.isType(FIGHTER)) ||
        (carrierUnit.isType(TRANSPORT) && cargoUnit.isLandUnit()));
    }
}