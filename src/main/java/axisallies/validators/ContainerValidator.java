package axisallies.validators;

import axisallies.units.Unit;

import static axisallies.units.UnitType.*;

public class ContainerValidator {
    public static boolean isValidType(Unit carrierUnit, Unit cargoUnit) {
        return ((carrierUnit.isType(AIRCRAFT_CARRIER) && cargoUnit.isType(FIGHTER)) ||
            (carrierUnit.isType(TRANSPORT) && cargoUnit.isLandUnit()));
    }
}