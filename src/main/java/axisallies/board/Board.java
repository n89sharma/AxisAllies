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
    
    private static final String BOARD_GAME_SETUP_FILE = "/Users/nik/Documents/development/AxisAlliesMaven/src/main/java/axisallies/1942-map.csv";
    private static final String INITIAL_UNIT_SETUP_FILE = "/Users/nik/Documents/development/AxisAlliesMaven/src/main/java/axisallies/1942-territory-details.csv";
    private static final String TERRITORY = "TERRITORY";
    private static final String NATION = "NATION";
    private static final String IPC = "IPC";

    public static MutableGraph<String> getBoardMap() throws IOException {

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

    public static Map<String, Map<String, String>> getTerritoryDetails() throws IOException {

        Map<String, Map<String, String>> territoryDetails = new HashMap<>();
        Iterable<CSVRecord> records = getFileRecords(INITIAL_UNIT_SETUP_FILE);
        
        for (CSVRecord record : records) {
            Map<String, String> territory = new HashMap<>();
            territoryDetails.put(trimGet(record, TERRITORY), territory);
            territory.put(NATION,   trimGet(record, NATION));
            territory.put(IPC,      trimGet(record, IPC));
            for (UnitType unitType : UnitType.values()) {
                territory.put(unitType.name(), trimGet(record, unitType.name()));
            }
        }
        return territoryDetails;
    }

    private static Iterable<CSVRecord> getFileRecords(String fileName) throws FileNotFoundException, IOException {
        return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(fileName)); 
    }

    private static String trimGet(CSVRecord record, String key) {
        return record.get(key).trim();
    }
}