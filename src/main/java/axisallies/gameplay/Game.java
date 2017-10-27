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

import static axisallies.nations.NationType.GERMANY;
import static axisallies.nations.NationType.USSR;
import static axisallies.nations.NationType.USA;
import static axisallies.nations.NationType.UK;
import static axisallies.nations.NationType.JAPAN;

public class Game {
    
    private static final String NATION = "NATION";

    private MutableGraph<String> boardSetup;
    private Map<String, Map<String, String>> unitSetup;
    private Map<String, NationType> players = new HashMap<>();
    private Map<NationType, Nation> nations = new HashMap<>();
    
    public Game() throws IOException {
        this.boardSetup = Board.boardSetup();
        this.unitSetup = Board.unitSetup();
        defaultPlayers();
        defaultNations();
    }

    public Map<NationType, Nation> getNations() {
        return nations;
    }

    private void defaultPlayers() {
        players.put("Nik",      GERMANY);
        players.put("Shaba",    USSR);
        players.put("Max",      USA);
        players.put("Hassan",   JAPAN);
        players.put("Vik",      UK);
    }

    private void defaultNations() {

        Map<NationType, List<String>>   territoriesByNation = new HashMap<>();
        Map<NationType, List<Unit>>     unitsByNation       = new HashMap<>(); 

        for(NationType nationType : NationType.values()) {
            territoriesByNation.put(nationType, new ArrayList<>());
            unitsByNation.put(      nationType, new ArrayList<>());
        }
        
        Map<String, String> tableRow;
        NationType nationType;

        for(String territoryName : unitSetup.keySet()) {
            tableRow    = unitSetup.get(territoryName);
            nationType  = NationType.valueOf(tableRow.get(NATION));

            territoriesByNation.get(nationType).add(territoryName);         
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