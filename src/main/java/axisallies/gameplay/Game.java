package axisallies.gameplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Collection;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.MultimapBuilder.SortedSetMultimapBuilder;
import com.google.common.graph.MutableGraph;

import axisallies.units.Unit;
import axisallies.units.UnitType;
import axisallies.nations.Nation;
import axisallies.nations.NationType;
import axisallies.GameResponse;
import axisallies.board.Board;
import axisallies.board.Territory;
import axisallies.board.Territory.TerritoryType;
import axisallies.tuple.Tuple;

import static axisallies.nations.NationType.GERMANY;
import static axisallies.nations.NationType.USSR;
import static axisallies.nations.NationType.USA;
import static axisallies.nations.NationType.UK;
import static axisallies.nations.NationType.JAPAN;
import static axisallies.units.UnitType.FIGHTER;

public class Game {

    private static final String NATION = "NATION";
    private static final String IPC_KEY = "IPC";
    private static final String TERRITORY_TYPE_KEY = "TERRITORY_TYPE";

    private MutableGraph<String> territoryGraph;
    private Map<String, NationType> players = new HashMap<>();
    private Map<NationType, Nation> nations = new HashMap<>();
    private Map<String, Territory> territories = new HashMap<>();
    private Map<Tuple<String, String>, Integer> shortestDistanceForTerritoryPair = new HashMap<>();;

    public Game() throws IOException {
        
        setTerritoryGraph();
        setAllNationsAndTerritories();
        compareDataSets();
        if(compareDataSets().isEmpty()) {
            setDefaultPlayers();
            setTerritoryDistances();
        }
    }

    public void run() {
        testOrderUnitsForNation();
        // testUnitsInTerritory();
        // testValidTerritoryMoves();
        // testTerritoryDistances();
        // testTuples();
    }

    private void testOrderUnitsForNation() {
        Map<UnitType, Integer> unitOrder = new HashMap<>();
        unitOrder.put(FIGHTER, 4);
        orderUnitsForNation(unitOrder, GERMANY);
        System.out.println(nations.get(GERMANY).getTreasuryAmount());
        nations.get(GERMANY)
            .getMobilizationUnit()
            .stream()
            .forEach(unit -> System.out.println(unit.getUnitType() + " : " + unit.getTerritory()));
    }

    //TODO: take out at a future date.
    private void testUnitsInTerritory() {
        getUnitsInATerritoryByType("Manchuria").entrySet()
            .stream()
            .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));
    }

    //TODO: take out at a future date.
    private void testValidTerritoryMoves() {

        getUnitsInATerritory("Manchuria").stream()
            .filter(unit -> unit.getUnitType().equals(FIGHTER))
            .map(unit -> getValidMoveTerritoriesForUnit(unit))
            .flatMap(map -> map.entrySet().stream())
            .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));
    }

    //TODO: take ot at a future date.
    private void testTerritoryDistances() {
        String selectedCountry = "Germany";
        for(Tuple<String, String> pair : shortestDistanceForTerritoryPair.keySet()) {
            String left = pair.getLeft();
            String right = pair.getRight();
            if(left.equals(selectedCountry) || right.equals(selectedCountry)) {
                System.out.println(pair + " : " + shortestDistanceForTerritoryPair.get(pair));
            }
        }
    }

    //TODO: take out at a future date.
    private void testTuples() {

        Tuple<String, String> pair = Tuple.of("India", "Canada");
        Tuple<String, String> inversePair = getInversePair(pair);
        Map<Tuple<String, String>, String> map = new HashMap<>();
        map.put(pair, "1");
        map.put(inversePair, "2");
        System.out.println(map);
        System.out.println(pair.equals(inversePair));
        System.out.println(pair.hashCode() + " : " + pair);
        System.out.println(inversePair.hashCode() + " : " + inversePair);
    }

    private void setTerritoryGraph() throws IOException {
        territoryGraph = Board.getTerritoryGraph();
    }

    private void setDefaultPlayers() {
        
        players.put("Nik", GERMANY);
        players.put("Shaba", USSR);
        players.put("Max", USA);
        players.put("Hassan", JAPAN);
        players.put("Vik", UK);
    }

    private void setAllNationsAndTerritories() throws IOException {

        Map<String, Map<String, String>> territoryDetails = Board.getTerritoryDetails();
        Map<NationType, Set<Territory>> territoriesByNation = new HashMap<>();
        Map<NationType, Set<Unit>> unitsByNation = new HashMap<>();

        for (NationType nationType : NationType.values()) {
            territoriesByNation.put(nationType, new HashSet<>());
            unitsByNation.put(nationType, new HashSet<>());
        }

        Map<String, String> territoryData;
        NationType nationType;
        TerritoryType territoryType;
        int ipcValue, numberOfUnits;
        Territory territory;
        for (String territoryName : territoryDetails.keySet()) {
            territoryData = territoryDetails.get(territoryName);
            nationType = NationType.valueOf(territoryData.get(NATION));
            ipcValue = Integer.parseInt(territoryData.get(IPC_KEY));
            territoryType = TerritoryType.valueOf(territoryData.get(TERRITORY_TYPE_KEY));

            territory = new Territory(territoryName, territoryType, nationType, ipcValue);
            territories.put(territory.getTerritoryName(), territory);
            territoriesByNation.get(nationType).add(territory);
            for (UnitType unitType : UnitType.values()) {
                numberOfUnits = Integer.parseInt(territoryData.get(unitType.toString()));
                for(int i=0; i<numberOfUnits; i++) {
                    unitsByNation.get(nationType).add(new Unit(territoryName, nationType, unitType));
                }
            }
        }

        nations.put(USSR, new Nation(USSR, territoriesByNation.get(USSR), unitsByNation.get(USSR)));
        nations.put(USA, new Nation(USA, territoriesByNation.get(USA), unitsByNation.get(USA)));
        nations.put(UK, new Nation(UK, territoriesByNation.get(UK), unitsByNation.get(UK)));
        nations.put(JAPAN, new Nation(JAPAN, territoriesByNation.get(JAPAN), unitsByNation.get(JAPAN)));
        nations.put(GERMANY, new Nation(GERMANY, territoriesByNation.get(GERMANY), unitsByNation.get(GERMANY)));
    }

    private Set<String> compareDataSets() {
        
        Set<String> errors = new HashSet<>();
        Set<String> territoryNamesFromGraph = territoryGraph.nodes();
        Set<String> territoryNamesFromDetails = territories.keySet(); 
        for(String territoryNameFromDetails : territoryNamesFromDetails) {
            if(!territoryNamesFromGraph.contains(territoryNameFromDetails)){
                errors.add(territoryNameFromDetails + " not present in the graph csv.");
            }
        }
        errors.stream()
            .forEach(error -> System.out.println(error));
        return errors;
    }

    private void setTerritoryDistances() {
        
        int maximumUnitMovementRange = maximumUnitMovementRange();
        for(String startingTerritoryName : territoryGraph.nodes()) {
            setAdjacentTerritoriesRecursively(0, maximumUnitMovementRange, startingTerritoryName, startingTerritoryName, shortestDistanceForTerritoryPair);
        }   
    }

    private Integer maximumUnitMovementRange() {
        
        return Arrays.stream(UnitType.values())
            .map(UnitType::getMovementRange)
            .reduce(Integer::max)
            .orElse(0);
    }

    private void setAdjacentTerritoriesRecursively(
        int previousDistance, 
        int maxDistance, 
        String startingTerritoryName, 
        String previousTerritory,
        Map<Tuple<String, String>, Integer> territoryDistanceMap) {

        int currentDistance = previousDistance + 1;
        if(currentDistance <= maxDistance) {
            Set<String> adjacentTerritories = territoryGraph.adjacentNodes(previousTerritory);
            for(String adjacentTerritoryName : adjacentTerritories){
                setDistanceInMap(territoryDistanceMap, currentDistance, startingTerritoryName, adjacentTerritoryName);
                setAdjacentTerritoriesRecursively(currentDistance, maxDistance, startingTerritoryName, adjacentTerritoryName, territoryDistanceMap);
            }
        }
    }

    private void setDistanceInMap(
        Map<Tuple<String, String>, Integer> territoryDistanceMap, 
        int distance, 
        String startingTerritoryName, 
        String adjacentTerritoryName) {

        Tuple<String, String> territoryPair = Tuple.of(startingTerritoryName, adjacentTerritoryName);
        int storedValue;
        if(territoryDistanceMap.containsKey(territoryPair)) {
            storedValue = territoryDistanceMap.get(territoryPair);   
            if(storedValue > distance) {
                territoryDistanceMap.put(territoryPair, distance);
            }         
        }
        else {
            territoryDistanceMap.put(territoryPair, distance);   
        }
    }

    private Tuple<String, String> getInversePair(Tuple<String, String> territoryPair) {
        return Tuple.of(territoryPair.getRight(), territoryPair.getLeft());
    }

    public Map<String, NationType> getPlayers() {
        return players;
    }

    public Map<NationType, Nation> getNations() {
        return nations;
    }

    public Map<String, Territory> getTerritories() {
        return territories;
    }

    public Set<Territory> getTerritoriesForNation(NationType nationType) {
        return nations.get(nationType).getTerritories();
    }

    public Set<Territory> getTerritoriesForPlayer(String playerName) {
        
        NationType nationType = players.get(playerName);
        return nations.get(nationType).getTerritories(); 
    }

    public Map<UnitType, Integer> getUnitsInATerritoryByType(String territoryName) {

        return getUnitsInATerritory(territoryName).stream()
            .collect(groupingBy(Unit::getUnitType, reducing(0, e -> 1, Integer::sum)));
    }

    public Set<Unit> getUnitsInATerritory(String territoryName) {
        
        return nations.entrySet()                                       //nation types, nation
            .stream()
            .map(Entry::getValue)                                       //nation
            .map(Nation::getUnits)                                      //list of unit
            .flatMap(Collection::stream)                                //unit
            .filter(unit -> unit.getTerritory().equals(territoryName))  //unit territory
            .collect(toSet());
    }

    public Set<String> getAdjacentTerritories(String territoryName) {
        return territoryGraph.adjacentNodes(territoryName);
    }

    public Map<String, Integer> getValidMoveTerritoriesForUnit(Unit unit) {

        String startingTerritory = unit.getTerritory();
        int maximumDistance = unit.getUnitType().getMovementRange();
        return shortestDistanceForTerritoryPair.entrySet().stream()
            .filter(entry -> territoryIsWithinDistance(entry, startingTerritory, maximumDistance))
            .collect(toMap(
                entry->kMap(entry, startingTerritory), 
                entry->vMap(entry)));
    }

    private String kMap(Entry<Tuple<String, String>, Integer> entry, String startingTerritory) {

        return entry.getKey().getLeft().equals(startingTerritory) 
            ? entry.getKey().getRight()
            : entry.getKey().getLeft();
    }

    private Integer vMap(Entry<Tuple<String, String>, Integer> entry) {
        return entry.getValue();
    }

    private boolean territoryIsWithinDistance(
        Entry<Tuple<String, String>, Integer> entry,
        String startingTerritory,
        int maximumDistance) {

        return (entry.getKey().getLeft().equals(startingTerritory) || 
            entry.getKey().getRight().equals(startingTerritory)) &&
            (entry.getValue() <= maximumDistance);
    }

    public GameResponse orderUnitsForNation(Map<UnitType, Integer> unitOrder, NationType nationType) {
        
        Nation nation = nations.get(nationType);
        GameResponse gameResponse = new GameResponse<Boolean>();
        int orderCost = unitOrder.entrySet()
            .stream()
            .mapToInt(entry -> entry.getKey().getProductionCost()*entry.getValue())
            .sum();

        if(orderCost <= nation.getTreasuryAmount()) {
            StringBuilder builder = new StringBuilder("Not enough points in the nation's treasury for the unit order.\n");
            builder.append("Nation : " + nationType.getName() + "\n");
            builder.append("Treasury : " + nation.getTreasuryAmount() + "\n");
            builder.append("Points required : " + orderCost + "\n");
            gameResponse.addError(builder.toString());
        }

        if(!gameResponse.hasErrors()) {
            nation.purchaseUnits(unitOrder);
        }

        return gameResponse;
    }
}