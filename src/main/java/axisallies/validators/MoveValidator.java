package axisallies.validators;

import axisallies.board.Territory;
import axisallies.nations.NationType;
import axisallies.units.Path;
import axisallies.units.Unit;

import java.util.Collection;
import java.util.Set;

import static axisallies.board.Board.areHostile;
import static java.util.stream.Collectors.toSet;

public class MoveValidator {

    public static boolean isUnitAtPathBeginning(Path path, Unit unit) {
        return path.getStart().containsUnit(unit);
    }

    public static boolean isPathWithinUnitRange(Path path, Unit unit) {
        return path.size() <= (unit.getUnitType().getMovementRange() - unit.getTravelledDistance());
    }

    public static boolean isHostileTerritoryPresentBeforeDestination(Path path, Unit unit) {
        return !getHostileTerritories(path.getAllBeforeDestination(), unit).isEmpty();
    }

    public static boolean isPathValidTerritoryTypeForUnit(Path path, Unit unit) {

        boolean validPathType = false;
        if (unit.isLandUnit()) {
            validPathType = path.getTerritories().stream().noneMatch(Territory::isSea);
        } else if (unit.isSeaUnit()) {
            validPathType = path.getTerritories().stream().noneMatch(Territory::isLand);
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

    protected static Set<Territory> getHostileTerritories(Path path, Unit unit) {
        return path.getTerritories().stream()
            .filter(territory -> areHostile(territory, unit))
            .collect(toSet());
    }

    protected static boolean hasHostileUnits(Set<Territory> hostileTerritories) {
        return hostileTerritories.stream()
            .map(Territory::getCompanyUnits)
            .flatMap(Collection::stream)
            .findFirst()
            .isPresent();
    }
}
