package axisallies.board;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.io.FileReader;
import java.nio.file.Paths;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import axisallies.units.UnitType;

import java.util.Arrays;
import java.util.List; 
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Board {
    
    private static final String BOARD_GAME_SETUP_FILE = "/Users/nik/Documents/development/AxisAlliesMaven/src/main/java/axisallies/axis-allies-1942-map.csv";
    private static final String INITIAL_UNIT_SETUP_FILE = "/Users/nik/Documents/development/AxisAlliesMaven/src/main/java/axisallies/board-setup.csv";
    private static final String TERRITORY = "TERRITORY";
    private static final String NATION = "NATION";

    public static MutableGraph<String> boardSetup() throws IOException {
        MutableGraph<String> graph = GraphBuilder.undirected().build();
        for(String line : Files.readAllLines(Paths.get(BOARD_GAME_SETUP_FILE))) {
            List<String> territoryAndNeighbours = new ArrayList<>(Arrays.asList(line.split(",")));
            String territory = territoryAndNeighbours.get(0);
            territoryAndNeighbours.remove(0);
            for(String neighbour: territoryAndNeighbours) {
                graph.putEdge(territory, neighbour);
            }
        }
        return graph;
    }

    public static Map<String, Map<String, String>> unitSetup() throws IOException {

        Map<String, Map<String, String>> unitSetup = new HashMap<>();
        Reader in = new FileReader(INITIAL_UNIT_SETUP_FILE);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        
        for (CSVRecord record : records) {
            Map<String, String> unitsAndNationByTerritory = new HashMap<>();
            unitSetup.put(trimGet(record, TERRITORY), unitsAndNationByTerritory);
            unitsAndNationByTerritory.put(NATION, trimGet(record, NATION));
            for (UnitType unitType : UnitType.values()) {
                unitsAndNationByTerritory.put(unitType.name(), trimGet(record, unitType.name()));
            }
        }
        return unitSetup;
    }

    private static String trimGet(CSVRecord record, String key) {
        return record.get(key).trim();
    }
}