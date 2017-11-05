package axisallies.validators;

import axisallies.board.Territory;
import axisallies.nations.NationType;
import axisallies.units.Unit;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class MoveValidator {

    public static boolean isUnitAtPathBeginning(List<Territory> path, Unit unit) {
        return path.get(0).getUnits().contains(unit);
    }

    public static boolean isPathWithinUnitRange(List<Territory> path, Unit unit) {
        return path.size() <= (unit.getUnitType().getMovementRange() - unit.getTravelledDistance());
    }

    public static boolean isHostileTerritoryPresentBeforeDestination(List<Territory> path, Unit unit) {
        return !getHostileTerritories(path.subList(0, path.size() - 1), unit).isEmpty();
    }

    public static boolean isPathValidTerritoryTypeForUnit(List<Territory> path, Unit unit) {

        boolean validPathType = false;
        if (unit.isLandUnit()) {
            validPathType = path.stream().noneMatch(Territory::isSea);
        } else if (unit.isSeaUnit()) {
            validPathType = path.stream().noneMatch(Territory::isLand);
        }
        return validPathType;
    }

    public static boolean areAllUnitsFromOneTeam(Set<Unit> units) {
        return 1 == units.stream()
                .map(Unit::getNationType)
                .map(NationType::getTeamType)
                .distinct()
                .count();
    }

    protected static Set<Territory> getHostileTerritories(List<Territory> path, Unit unit) {
        return path.stream()
                .filter(territory -> territory.isHostileTo(unit))
                .collect(toSet());
    }

    protected static boolean hasHostileUnits(Set<Territory> hostileTerritories) {
        return hostileTerritories.stream()
                .map(Territory::getUnits)
                .flatMap(Collection::stream)
                .findFirst()
                .isPresent();
    }
}
