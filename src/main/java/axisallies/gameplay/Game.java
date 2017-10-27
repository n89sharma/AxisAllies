package axisallies.gameplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private MutableGraph<String> boardMap;
    private Map<String, NationType> players = new HashMap<>();
    private Map<NationType, Nation> nations = new HashMap<>();
    
    public Game() throws IOException {
        this.boardMap = Board.getBoardMap();
        defaultPlayers();
        defaultNations();
    }

    public MutableGraph<String> getBoardSetup() {
        return boardMap;
    }

    public Map<NationType, Nation> getNations() {
        return nations;
    }

    public void run() {
        System.out.println(nations.get(GERMANY).getTerritories().get(0).getIpc());
        System.out.println(nations.get(GERMANY).getTerritories().get(0).getTerritoryName());
    }

    private void defaultPlayers() {
        players.put("Nik",      GERMANY);
        players.put("Shaba",    USSR);
        players.put("Max",      USA);
        players.put("Hassan",   JAPAN);
        players.put("Vik",      UK);
    }

    private void defaultNations() throws IOException{

        Map<String, Map<String, String>> territoryDetails   =  Board.getTerritoryDetails();
        Map<NationType, List<Territory>>    territoriesByNation = new HashMap<>();
        Map<NationType, List<Unit>>      unitsByNation       = new HashMap<>(); 

        for(NationType nationType : NationType.values()) {
            territoriesByNation.put(nationType, new ArrayList<>());
            unitsByNation.put(      nationType, new ArrayList<>());
        }
        
        Map<String, String> territoryData;
        NationType nationType;
        int ipcValue;
        for(String territoryName : territoryDetails.keySet()) {
            territoryData   = territoryDetails.get(territoryName);
            nationType      = NationType.valueOf(territoryData.get(NATION));
            ipcValue        = Integer.parseInt(territoryData.get(IPC_KEY));

            territoriesByNation.get(nationType).add(new Territory(territoryName, nationType, ipcValue));       
            for (UnitType unitType : UnitType.values()) {
                unitsByNation.get(nationType).add(new Unit(territoryName, nationType, unitType));
            }
        }

        nations.put(USSR,       new Nation(USSR,      territoriesByNation.get(USSR),      unitsByNation.get(USSR)));
        nations.put(USA,        new Nation(USA,       territoriesByNation.get(USA),       unitsByNation.get(USA)));
        nations.put(UK,         new Nation(UK,        territoriesByNation.get(UK),        unitsByNation.get(UK)));
        nations.put(JAPAN,      new Nation(JAPAN,     territoriesByNation.get(JAPAN),     unitsByNation.get(JAPAN)));
        nations.put(GERMANY,    new Nation(GERMANY,   territoriesByNation.get(GERMANY),   unitsByNation.get(GERMANY)));
    }

}