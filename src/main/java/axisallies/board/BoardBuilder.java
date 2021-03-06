package axisallies.board;

import axisallies.nations.Nation;
import axisallies.nations.NationType;
import axisallies.players.Player;
import axisallies.units.Unit;
import axisallies.units.UnitType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private static final String USSR_LAND_3_TERRITORIES_JSON = "ussr_land_3_territories.json";
    private static final String TEST_PLAYER_SETUP = "sample_players.json";

    public static Board testBuild(String testTerritoryMap, String testPlayerSetupFile) throws IOException {
        return build(UnitType.class, testTerritoryMap, testPlayerSetupFile);
    }

    public static Board sourceBuild() throws IOException {
        return build(BoardBuilder.class, GAME_TERRITORY_MAP_SETUP, PLAYER_SETUP_FILE);
    }

    private static void createTerritoryMap(Board board, String boardGameSetupFile, Class clazz) throws IOException {

        String jsonFilePath = getResourcePath(boardGameSetupFile, clazz);
        File jsonFile = new File(jsonFilePath);
        TypeReference<Set<Territory>> typeRef = new TypeReference<Set<Territory>>() {
        };
        ObjectMapper mapper = new ObjectMapper();


        Set<Territory> territories = mapper.readValue(jsonFile, typeRef);

        Map<String, Territory> territoryMap = territories
            .stream()
            .collect(toMap(Territory::getTerritoryName, identity()));

        for (Territory territory : territories) {
            Set<Territory> neighbours = new HashSet<>();
            for (String neighbourName : territory.getNeighbourNames()) {
                if (null != territoryMap.get(neighbourName)) {
                    neighbours.add(territoryMap.get(neighbourName));
                }
            }
            territory.setNeighbours(neighbours);
        }

        board.setTerritories(territoryMap);
    }

    private static void createPlayers(Board board, String playerSetupFile, Class clazz) throws IOException {

        String jsonFilePath = getResourcePath(playerSetupFile, clazz);
        Set<Player> players = new ObjectMapper().readValue(
            new File(jsonFilePath),
            new TypeReference<Set<Player>>() {
            });

        board.setPlayers(players.stream().collect(toMap(Player::getNationType, identity())));
    }

    private static void createNations(Board board) {

        Map<NationType, Nation> nations = Arrays.stream(NationType.values())
            .collect(toMap(identity(), Nation::new));

        for (String territoryName : board.getTerritoryNames()) {
            Territory territory = board.get(territoryName);
            nations.get(territory.getNationType()).addTerritory(territory);

            for (Unit unit : territory.getCompanyUnits()) {
                nations.get(unit.getNationType()).addUnit(unit);
            }
        }

        board.setNations(nations);
    }

    private static String getResourcePath(String fileName, Class clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        return classLoader.getResource(fileName).getPath();
    }

    private static Board build(Class clazz, String mapFile, String playerFile) throws IOException {
        Board board = new Board();
        createTerritoryMap(board, mapFile, clazz);
        createPlayers(board, playerFile, clazz);
        createNations(board);
        return board;
    }
}
