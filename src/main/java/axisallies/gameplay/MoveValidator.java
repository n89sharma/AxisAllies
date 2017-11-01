package axisallies.gameplay;

import axisallies.board.Territory;
import axisallies.tuple.Tuple;
import axisallies.units.Unit;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

import static axisallies.units.UnitType.BOMBER;
import static axisallies.units.UnitType.FIGHTER;
import static axisallies.units.UnitType.DESTROYER;
import static axisallies.units.UnitType.TANK;
import static axisallies.units.UnitType.SUBMARINE;

public class MoveValidator {

    // TANK
    // FIGHTER
    // BOMBER
    // DESTROYER
    // SUBMARINE
    // TRANSPORT

    // INFANTRY
    // ARTILLERY
    // AA_GUN
    // CRUISER
    // AIRCRAFT_CARRIER
    // BATTLESHIP

    public static boolean isMoveValid(
        List<String> path, 
        Unit unit, 
        GamePhaseType phaseType, 
        Map<Tuple<String, String>, Integer> shortestDistanceForTerritoryPair) {
        return true;
    }

    private String concatStrings(String... inputStrings) {
        StringBuilder stringBuilder = new StringBuilder("");
        for(String inputString : inputStrings) {
            stringBuilder.append(inputString);
        }
        return stringBuilder.toString();
    }

    private boolean validateCombatLandUnitMoves(List<Territory> path, Unit unit, Map<Tuple<String, String>, Integer> distance) {

        Territory fromTerritory = path.get(0);
        Territory toTerritory = path.get(path.size()-1);
        Tuple<String, String> territoryPair = Tuple.of(fromTerritory.getTerritoryName(), toTerritory.getTerritoryName());

        path.remove(path.get(0));
        path.remove(path.get(path.size()-1));
        List<Territory> hostileTerritoriesInPath   = path.stream()
            .filter(territory -> territory.isHostileTo(unit))
            .collect(toList());
        long enemyUnitsBeforeDestination = hostileTerritoriesInPath.stream()
            .flatMap(territory -> territory.getUnits().stream())
            .count();

            
        if( (unit.is(TANK) && enemyUnitsBeforeDestination > 0) || 
            (unit.is(TANK) && toTerritory.isFriendlyTo(unit) && hostileTerritoriesInPath.isEmpty()) ||
            (!unit.is(TANK) && toTerritory.isFriendlyTo(unit)) ||
            (distance.containsKey(territoryPair) && 
                distance.get(territoryPair) > unit.getCurrentRange()) ||
            (!distance.containsKey(territoryPair))) {
            return false;
        }
        return true;
    }

    private boolean validateCombatAirUnitMoves(
        List<Territory> path, 
        Unit unit, 
        Map<String, Territory> territories,
        Map<Tuple<String, String>, Integer> distance) {
        
        Tuple<String, String> territoryPair = Tuple.of(path.get(0).getTerritoryName(), path.get(path.size()-1).getTerritoryName());
        Territory toTerritory = path.get(path.size()-1);

        if( (!distance.containsKey(territoryPair)) ||
            (distance.containsKey(territoryPair) && distance.get(territoryPair) > unit.getCurrentRange()) || 
            (distance.containsKey(territoryPair) && (distance.get(territoryPair) == unit.getCurrentRange()) && unit.is(BOMBER)) ||
            (distance.containsKey(territoryPair) && (distance.get(territoryPair) == unit.getCurrentRange()) && unit.is(FIGHTER) && !toTerritory.isSeaTerritory())) {
            return false;
        }
        return true;
    }

    private boolean validateCombatSeaUnitMoves(
        List<Territory> path, 
        Unit unit, 
        Map<String, Territory> territories,
        Map<Tuple<String, String>, Integer> distance) {

        path.remove(path.size()-1);
        long hostileDestroyerUnits = path.stream()
            .flatMap(territory -> territory.getUnits().stream())
            .filter(otherUnit -> otherUnit.isHostileTo(unit))
            .filter(otherUnit -> otherUnit.getUnitType().equals(DESTROYER))
            .count();

        if(unit.is(SUBMARINE) && hostileDestroyerUnits > 0) {
            return false;
        }
        return true;
    }

    private String getCombatMoveMustEndInCombatError(String fromTerritory, Territory fTerritory, Territory tTerritory) {
        return concatStrings(
            "Combat phase moves must result in combat.", 
            " Starting territory ",
            fromTerritory,
            " is occupied by ",
            fTerritory.getNationType().getName(),
            " and destination territory is occupied by ",
            tTerritory.getNationType().getName());
    }

    private String getAirUnitsMustReserveRangeInCombatError(int shortestDistance, int unitMaximumRange) {
        return concatStrings(
            "Air combat units must retain some of their range in the combat phase.",
            "Shortest distance is ",
            Integer.toString(shortestDistance),
            " and current range for unit is ",
            Integer.toString(unitMaximumRange));
    }

    private String getTerritoryOutOfRangeError(String fromTerritory, String toTerritory) {
        return concatStrings(
            "Shortest distance between",
            fromTerritory,
            " and ",
            toTerritory,
            " is larger than the range of any unit.");
    }

    private String getTerritoryOutOfUnitRangeError(String fromTerritory, Tuple<String, String> territoryPair, Unit unit, Map<Tuple<String, String>, Integer> distance) {
        return concatStrings(
            "The unit cannot reach ",
            " from ",
            fromTerritory, 
            ". Shortest distance is ",
            distance.get(territoryPair).toString(),
            " and ",
            unit.getUnitType().name(),
            " has a range of ",
            Integer.toString(unit.getUnitType().getMovementRange()),
            ".");
    }

    private String getCombatMovePathHasEnemyTerritoriesBeforeDestinationError() {
        return "The selected path has enemy territories before";
    }

    private String getCannotBlitzWhenEnemyPresentBeforeDestinationError() {
        return "You can not blitz with a tank when there are enemies in territories before the destination.";
    }
}