package axisallies.board;

import axisallies.nations.Nation;
import axisallies.nations.NationType;
import axisallies.players.Player;
import axisallies.units.Unit;
import axisallies.units.UnitType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class BoardBuilder {

    private static final String GAME_TERRITORY_MAP_SETUP = "1942-second-edition.json";
    private static final String PLAYER_SETUP_FILE = "sample_players.json";
    private static final String TEST_MAP_SETUP = "sample_territory.json";
    private static final String TEST_PLAYER_SETUP = "sample_players.json";

    private static Map<String, Territory> createTerritoryMap(String boardGameSetupFile, Class clazz) throws IOException {

        String jsonFilePath = getResourcePath(boardGameSetupFile, clazz);
        Set<Territory> territories = new ObjectMapper().readValue(
                new File(jsonFilePath),
                new TypeReference<Set<Territory>>() {
                });

        return territories.stream().collect(toMap(Territory::getTerritoryName, identity()));
    }

    private static MutableGraph<Territory> createTerritoryGraph(Map<String, Territory> territoryMap) {

        Set<String> missingTerritories = new HashSet<>();
        MutableGraph<Territory> territoryGraph = GraphBuilder.undirected().build();
        for (Territory territory : territoryMap.values()) {
            for (String neighbourName : territory.getNeighbourNames()) {
                if (null != territoryMap.get(neighbourName)) {
                    territoryGraph.putEdge(territory, territoryMap.get(neighbourName));
                }
            }
        }

        return territoryGraph;
    }

    private static Map<NationType, Player> createPlayers(String playerSetupFile, Class clazz) throws IOException {

        String jsonFilePath = getResourcePath(playerSetupFile, clazz);
        Set<Player> players = new ObjectMapper().readValue(
                new File(jsonFilePath),
                new TypeReference<Set<Player>>() {
                });

        return players.stream().collect(toMap(Player::getNationType, identity()));
    }

    private static Map<NationType, Nation> createNations(Map<String, Territory> territories) {

        Map<NationType, Nation> nations = Arrays.stream(NationType.values())
                .collect(toMap(identity(), Nation::new));

        for (String territoryName : territories.keySet()) {
            Territory territory = territories.get(territoryName);
            nations.get(territory.getNationType()).addTerritory(territory);

            for (Unit unit : territory.getUnits()) {
                nations.get(unit.getNationType()).addUnit(unit);
            }
        }
        return nations;
    }

    private static String getResourcePath(String fileName, Class clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        return classLoader.getResource(fileName).getPath();
    }

    public static Board testBuild() throws IOException {
        return build(UnitType.class, TEST_MAP_SETUP, TEST_PLAYER_SETUP);
    }

    public static Board sourceBuild() throws IOException {
        return build(BoardBuilder.class, GAME_TERRITORY_MAP_SETUP, PLAYER_SETUP_FILE);
    }

    private static Board build(Class clazz, String mapFile, String playerFile) throws IOException {
        Board board = new Board();
        board.setTerritories(createTerritoryMap(mapFile, clazz));
        board.setGraph(createTerritoryGraph(board.getTerritories()));
        board.setPlayers(createPlayers(playerFile, clazz));
        board.setNations(createNations(board.getTerritories()));
        return board;
    }
}
