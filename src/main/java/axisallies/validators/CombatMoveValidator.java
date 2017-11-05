package axisallies.validators;

import axisallies.board.Territory;
import axisallies.units.CarrierUnit;
import axisallies.units.Unit;
import static axisallies.units.UnitType.AIRCRAFT_CARRIER;
import static axisallies.nations.NationType.GERMANY;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static axisallies.units.UnitType.*;

public class CombatMoveValidator extends MoveValidator {

    public static boolean isDestinationHostile(List<Territory> path, Unit unit) {
        return path.get(path.size() - 1).isHostileTo(unit);
    }

    public static boolean isValidTankBlitz(List<Territory> path, Unit unit) {
        //if no enemy units are present before destination and the unit is tank
        return unit.isType(TANK) &&
                !hasHostileUnits(getHostileTerritories(path.subList(0, path.size() - 1), unit));
    }

    public static boolean isValidAmphibiousAssault(List<Territory> path, Set<Unit> units) {

        Unit unit = (Unit) units.toArray()[0];
        boolean allUnitsAreSeaUnits = units.stream().allMatch(Unit::isSeaUnit);
        boolean destinationHasHostileLandNeighbour = path.get(path.size() - 1)
                .getNeighbours()
                .stream()
                .anyMatch(territory -> territory.isHostileTo(unit));
        boolean transportPresentWithUnits = units.stream()
                .filter(u -> u.getUnitType().equals(TRANSPORT))
                .map(Unit::getUnits)
                .flatMap(Collection::stream)
                .findFirst()
                .isPresent();
        return allUnitsAreSeaUnits &&
                destinationHasHostileLandNeighbour &&
                transportPresentWithUnits;
    }

    public static boolean isValidCombatRetreat(List<Territory> path, Set<Unit> units) {

        boolean currentLocationIsHostile = units.stream().allMatch(unit -> path.get(0).isHostileTo(unit));
        boolean destinationIsFriendly = units.stream().allMatch(unit -> path.get(0).isFriendlyTo(unit));
        return currentLocationIsHostile && destinationIsFriendly;
    }

    public static boolean isValidSubmarineTransportAssault(List<Territory> path, Unit unit) {

        return path.get(path.size() - 1)
                .getUnits()
                .stream()
                .filter(otherUnit -> otherUnit.isHostileTo(unit))
                .anyMatch(otherUnit -> otherUnit.isType(SUBMARINE) || otherUnit.isType(TRANSPORT));
    }

    public static boolean isValidBomberMove(List<Territory> path, Unit unit) {
        return (path.size() - 1) < (unit.getUnitType().getMovementRange() - unit.getTravelledDistance());
    }

    public static boolean isValidFighterMove(List<Territory> path, Unit unit) {

        boolean unitRangeLeft = (path.size() - 1) <
                (unit.getUnitType().getMovementRange() - unit.getTravelledDistance());
        boolean unitRangeReachesDestination = (path.size() - 1) <=
                (unit.getUnitType().getMovementRange() - unit.getTravelledDistance());
        boolean destinationIsSea = path.get(path.size() - 1).isSea();
        return (unitRangeLeft || (unitRangeReachesDestination && destinationIsSea));
    }

    public static boolean isValidAmphibiousAssaultOffloading(List<Territory> path, Unit unit) {

        return path.get(0)
                .getUnits()
                .stream()
                .filter(otherUnit -> otherUnit.isHostileTo(unit))
                .noneMatch(otherUnit -> !otherUnit.isType(SUBMARINE) && !otherUnit.isType(TRANSPORT));
    }

    public static boolean isValidSubmarineMove(List<Territory> path, Unit unit) {

        return path.stream()
                .map(Territory::getUnits)
                .flatMap(Collection::stream)
                .filter(otherUnit -> otherUnit.isHostileTo(unit))
                .noneMatch(otherUnit -> otherUnit.isType(DESTROYER));
    }

    public void testMethod() {
        CarrierUnit unit = new CarrierUnit(AIRCRAFT_CARRIER);

    }
}

/**
 * PAGE 11
 *  movement in hostile territory is combat                                     (/)
 *  you cannot end in friendly space except:
 *      blitzing tanks                                                          (/)
 *      amphibious assault from uncontested sea zone                            (/)
 *      sea units comat retreat                                                 (/)
 *      sea units submarine/transport assault                                   (/)
 * you cannot move units, conduct combat and then move units again except:
 *      land units making amphibious assault                                    (/)
 * land or sea units move up to their movement.                                 (/)
 * units stop when they enter hostile space.                                    (/)
 * 
 * enemy sub or transport does not stop movement or offloading/ loading.        (/)
 * if a warship attacks a sub/ transport then it must end its move.             (/)
 * 
 * PAGE 12
 *  Sea units starting in hostile zones
 *      remain in sea zone conduct combat.                  
 *      leave sea zone.
 *      leave sea zone load units conduct combat elsewhere.
 *      leave sea zone load units and com back.                                 X
 *      cannot participate in non combat move .                                 X
 *  Air units
 *      reserve move for non combat move.                                       (/)
 *      fighter can use up its range but then must land on carrier.             (/)
 * Amphibious assault
 *      sea zone must be made friendly before offloading units.                 (/)
 *      you have to announce the assault?
 *      air units may participate in either sea combat or land combat.
 * 
 * PAGE 13
 *  Special combat moves
 *      Aircraft Carriers
 *          fighters on aircraft carriers launch when carrier moves in combat   
 *          phase. 
 *          guest fighters cannot take part in combat if the carrier moves in 
 *          combat phase.
 *          guest fighters can land when carrier when it finishes its move
 *      Submarines
 *          submarine can ignore enemy units while moving other than a          (/)
 *          hostile destroyer.
 *      Tanks
 *          tanks can blitz through an unoccupied hostile territory             (/)
 *          may end in a friendly territory
 *          if tank ecounters hostile units it has to stop
 *      Transports
 *          if transport encounters hostile surface units after it starts
 *          moving it must end its turn
 *          if units are loaded in combat phase they must be offloaded in 
 *          combat phase
 *          transport cannot offload units when submarines present in the       (/)
 *          sea zone unless firendly surface warships are accompanying the 
 *          transport
 * 
 */
