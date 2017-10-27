package axisallies.board;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import axisallies.units.UnitType;

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
        Iterable<CSVRecord> records = getFileRecords(BOARD_GAME_SETUP_FILE);

        for(CSVRecord record : records) {
            List<String> territoryAndNeighbours = new ArrayList<>();
            Iterable<String> it = () -> record.iterator();
            for(String recordValue : it) {
                territoryAndNeighbours.add(recordValue.trim());
            }
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
        Iterable<CSVRecord> records = getFileRecords(INITIAL_UNIT_SETUP_FILE);
        
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

    private static Iterable<CSVRecord> getFileRecords(String fileName) throws FileNotFoundException, IOException {
        return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(fileName)); 
    }

    private static String trimGet(CSVRecord record, String key) {
        return record.get(key).trim();
    }
}