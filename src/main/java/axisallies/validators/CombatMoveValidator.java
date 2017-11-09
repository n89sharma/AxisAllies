package axisallies.validators;

import axisallies.board.Territory;
import axisallies.units.CarrierUnit;
import axisallies.units.Unit;
import static axisallies.units.UnitType.AIRCRAFT_CARRIER;
import static axisallies.nations.NationType.GERMANY;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static axisallies.units.UnitType.TANK;
import static java.util.stream.Collectors.toSet;

public class CombatMoveValidator {

    public static boolean isCombatMove(List<Territory> path, Unit unit) {
        return path.get(path.size() - 1).isHostileTo(unit);
    }

    public static boolean isValidBlitz(List<Territory> path, Unit unit) {
        //has to have gone through non occupied enemy territory before the last territory
        path.remove(path.size() - 1);
        Set<Territory> hostileTerritories = getHostileTerritories(path, unit);
        boolean hasHostileUnits = hasHostileUnits(hostileTerritories);
        return unit.isType(TANK) && !hostileTerritories.isEmpty() && !hasHostileUnits;
    }

    private static Set<Territory> getHostileTerritories(List<Territory> path, Unit unit) {
        return path.stream()
                .filter(territory -> territory.isHostileTo(unit))
                .collect(toSet());
    }

    private static boolean hasHostileUnits(Set<Territory> hostileTerritories) {
        return hostileTerritories.stream()
                .map(Territory::getUnits)
                .flatMap(Collection::stream)
                .findFirst()
                .isPresent();
    }

    public void testMethod() {
        CarrierUnit unit = new CarrierUnit(AIRCRAFT_CARRIER);

    }
}
