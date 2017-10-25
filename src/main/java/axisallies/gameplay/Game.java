package axisallies.gameplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.graph.MutableGraph;
import axisallies.nations.Nation;
import axisallies.nations.NationType;

public class Game {

    private MutableGraph<String> map;
    private Map<NationType, String> players = new HashMap<>();
    private List<Nation> nations = new ArrayList<>();
    
    public Game() throws IOException {
        this.map = axisallies.board.Map.getMap();
        defaultNations();
        defaultPlayers();
    }

    private void defaultPlayers() {
        players.put(NationType.GERMANY, "Nik");
        players.put(NationType.USSR,    "Shaba");
        players.put(NationType.USA,     "Max");
        players.put(NationType.JAPAN,   "Hassan");
        players.put(NationType.UK,      "Dania");
    }

    private void defaultNations() {
        Nation Germany = new Nation(NationType.GERMANY, new ArrayList<>(), new ArrayList<>());
    }

}