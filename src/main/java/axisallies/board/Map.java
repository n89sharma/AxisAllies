package axisallies.board;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.util.Arrays;
import java.util.List; 
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class Map {
    
    private static final String mapFilePath = "/Users/nik/Documents/development/AxisAlliesMaven/src/main/java/axisallies/axis-allies-1942-map.csv";
    private static final String initialBoardSetup = "/Users/nik/Documents/development/AxisAlliesMaven/src/main/java/axisallies/board-setup.csv";

    public static void run() throws IOException {
        // System.out.println(getMap());
        System.out.println(boardSetup());
    }

    public static MutableGraph<String> getMap() throws IOException {
        MutableGraph<String> graph = GraphBuilder.undirected().build();
        for(String line : Files.readAllLines(Paths.get(mapFilePath))) {
            List<String> regionAndNeighbours = new ArrayList<>(Arrays.asList(line.split(",")));
            String region = regionAndNeighbours.get(0);
            regionAndNeighbours.remove(0);
            for(String neighbour: regionAndNeighbours) {
                graph.putEdge(region, neighbour);
            }
        }
        return graph;
    }

    private static List<Region> boardSetup() throws IOException{
        CsvSchema schema = CsvSchema.builder()
            .addColumn("regionName")
            .addColumn("nationType")
            .addColumn("infantry")
            .addColumn("artillery")
            .addColumn("tanks")
            .addColumn("fighters")
            .addColumn("bombers")
            .addColumn("aaGuns")
            .addColumn("industrialComplexes")
            .addColumn("destroyer")
            .addColumn("transport")
            .addColumn("cruiser")
            .addColumn("aircraftCarrier")
            .addColumn("submarine")
            .addColumn("battleship")
            .build();

        MappingIterator<Region> it = new CsvMapper().readerFor(Region.class)
                .with(schema)
                .readValues(new File(initialBoardSetup));
        return it.readAll();
    }
}