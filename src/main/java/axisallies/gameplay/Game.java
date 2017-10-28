package axisallies.gameplay;

import java.io.IOException;
import java.util.HashSet;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import com.google.common.graph.MutableGraph;

import axisallies.units.Unit;
import axisallies.units.UnitType;
import axisallies.nations.Nation;
import axisallies.nations.NationType;
import axisallies.board.Board;
import axisallies.board.Territory;

import static axisallies.nations.NationType.GERMANY;
import static axisallies.nations.NationType.USSR;
import static axisallies.nations.NationType.USA;
import static axisallies.nations.NationType.UK;
import static axisallies.nations.NationType.JAPAN;

public class Game {

    private static final String NATION = "NATION";
    private static final String IPC_KEY = "IPC";

    private MutableGraph<String> territoryGraph;
    private Map<String, NationType> players = new HashMap<>();
    private Map<NationType, Nation> nations = new HashMap<>();
    private Map<String, Territory> territories = new HashMap<>();
    private Map<Pair<String, String>, Integer> territoryDistance;

    public Game() throws IOException {
        
        this.territoryGraph = Board.getTerritoryGraph();
        setDefaultPlayers();
        setAllNationsAndTerritories();
        setTerritoryDistances();
    }

    public void run() {
        territories.forEach((key, value) -> System.out.println(key + " : " + value.getIpc()));
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
        int ipcValue;
        Territory territory;
        for (String territoryName : territoryDetails.keySet()) {
            territoryData = territoryDetails.get(territoryName);
            nationType = NationType.valueOf(territoryData.get(NATION));
            ipcValue = Integer.parseInt(territoryData.get(IPC_KEY));

            territory = new Territory(territoryName, nationType, ipcValue);
            territories.put(territory.getTerritoryName(), territory);
            territoriesByNation.get(nationType).add(territory);
            for (UnitType unitType : UnitType.values()) {
                unitsByNation.get(nationType).add(new Unit(territoryName, nationType, unitType));
            }
        }

        nations.put(USSR, new Nation(USSR, territoriesByNation.get(USSR), unitsByNation.get(USSR)));
        nations.put(USA, new Nation(USA, territoriesByNation.get(USA), unitsByNation.get(USA)));
        nations.put(UK, new Nation(UK, territoriesByNation.get(UK), unitsByNation.get(UK)));
        nations.put(JAPAN, new Nation(JAPAN, territoriesByNation.get(JAPAN), unitsByNation.get(JAPAN)));
        nations.put(GERMANY, new Nation(GERMANY, territoriesByNation.get(GERMANY), unitsByNation.get(GERMANY)));
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

    public Set<Unit> getUnitsInATerritory(String territoryName) {
        
        return nations.entrySet()                                       //nation types, nation
            .stream()
            .map(Entry::getValue)                                       //nation
            .map(Nation::getUnits)                                      //list of unit
            .flatMap(Collection::stream)                                //unit
            .filter(unit -> unit.getTerritory().equals(territoryName))  //unit territory
            .collect(Collectors.toSet());
    }

    public Set<String> getAdjacentTerritories(String territoryName) {
        return territoryGraph.adjacentNodes(territoryName);
    }

    private void setTerritoryDistances() {
        //TODO: set territory distances
        this.territoryDistance = new HashMap<>();
    }

    public Map<Integer, Set<String>> getValidMoveTerritoriesForUnit(Unit unit) {
        //TODO: implement valid territories
        return new HashMap<>();
    }

}