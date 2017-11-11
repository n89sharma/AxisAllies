package axisallies.validators;

import static axisallies.units.UnitType.DESTROYER;
import static axisallies.units.UnitType.SUBMARINE;
import static axisallies.units.UnitType.TANK;
import static axisallies.units.UnitType.TRANSPORT;
import static axisallies.board.Board.areHostile;
import java.util.Collection;

import axisallies.board.Territory;
import axisallies.units.Company;
import axisallies.units.Path;
import axisallies.units.Unit;

public class CombatMoveValidator extends MoveValidator {

    public static boolean isDestinationHostile(Path path, Unit unit) {
        return areHostile(path.getDestination(), unit);
    }

    public static boolean isValidTankBlitz(Path path, Unit unit) {
        //if no enemy units are present before destination and the unit is tank
        return unit.isType(TANK) && !hasHostileUnits(getHostileTerritories(path.getAllBeforeDestination(), unit));
    }

    public static boolean isValidAmphibiousAssault(Path path, Company company) {

        boolean allUnitsAreSeaUnits = company.areAllSeaUnits();
        boolean destinationHasHostileLandNeighbour = path.getDestination()
                .getNeighbours()
                .stream()
                .anyMatch(territory -> company.isTerritoryHostile(territory));
        boolean transportPresentWithUnits = company.hasType(TRANSPORT);
        return allUnitsAreSeaUnits &&
                destinationHasHostileLandNeighbour &&
                transportPresentWithUnits;
    }

    public static boolean isValidCombatRetreat(Path path, Company company) {

        boolean currentLocationIsHostile = company.isTerritoryHostile(path.getStart());
        boolean destinationIsFriendly = company.areAllFriendlyToTerritory(path.getDestination());
        return currentLocationIsHostile && destinationIsFriendly;
    }

    public static boolean isValidSubmarineTransportAssault(Path path, Unit unit) {

        return path.getDestination()
                .getUnits()
                .stream()
                .filter(otherUnit -> areHostile(otherUnit, unit))
                .anyMatch(otherUnit -> otherUnit.isType(SUBMARINE) || otherUnit.isType(TRANSPORT));
    }

    public static boolean isValidBomberMove(Path path, Unit unit) {
        return (path.size() - 1) < (unit.getUnitType().getMovementRange() - unit.getTravelledDistance());
    }

    public static boolean isValidFighterMove(Path path, Unit unit) {

        boolean unitRangeLeft = (path.size() - 1) <
                (unit.getUnitType().getMovementRange() - unit.getTravelledDistance());
        boolean unitRangeReachesDestination = (path.size() - 1) <=
                (unit.getUnitType().getMovementRange() - unit.getTravelledDistance());
        boolean destinationIsSea = path.getDestination().isSea();
        return (unitRangeLeft || (unitRangeReachesDestination && destinationIsSea));
    }

    public static boolean isValidAmphibiousAssaultOffloading(Path path, Unit unit) {

        return path.getStart()
                .getUnits()
                .stream()
                .filter(otherUnit -> areHostile(otherUnit, unit))
                .noneMatch(otherUnit -> !otherUnit.isType(SUBMARINE) && !otherUnit.isType(TRANSPORT));
    }

    public static boolean isValidSubmarineMove(Path path, Unit unit) {

        return path.getTerritories().stream()
                .map(Territory::getUnits)
                .flatMap(Collection::stream)
                .filter(otherUnit -> areHostile(otherUnit, unit))
                .noneMatch(otherUnit -> otherUnit.isType(DESTROYER));
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
