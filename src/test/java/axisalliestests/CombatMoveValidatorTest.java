package axisalliestests;

import axisallies.board.BoardBuilder;
import axisallies.board.Territory;
import axisallies.units.Unit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static axisallies.nations.NationType.GERMANY;
import static axisallies.nations.NationType.USSR;
import static axisallies.units.UnitType.SUBMARINE;
import static axisallies.units.UnitType.TANK;
import static axisallies.validators.CombatMoveValidator.isDestinationHostile;
import static axisallies.validators.CombatMoveValidator.isValidTankBlitz;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CombatMoveValidatorTest {

    private static final String USSR_SEA_3_TERRITORIES_JSON = "ussr_sea_3_territories.json";
    private static final String GERMANY_USSR_LAND_3_TERRITORIES_JSON = "germany_ussr_land_3_territories.json";
    private static final String USSR_AMPHIBIOUS_ASSAULT  = "ussr_amphibious_asault.json";
    private static final String TEST_PLAYERS = "sample_players.json";

    @Test
    public void testIsCombatMove() throws IOException {

        List<Territory> path = getTestPathThreeTerritories(
                BoardBuilder.testBuild(
                        USSR_SEA_3_TERRITORIES_JSON,
                        TEST_PLAYERS).getTerritories());
        Unit germanSub = new Unit(null, GERMANY, SUBMARINE, null, 0, null);
        Unit russianSub = new Unit(null, USSR, SUBMARINE, null, 0, null);

        assertTrue(isDestinationHostile(path, germanSub));
        assertFalse(isDestinationHostile(path, russianSub));
    }

    @Test
    public void testIsValidBlitz() throws IOException {

        Map<String, Territory> territories = BoardBuilder.testBuild(
                GERMANY_USSR_LAND_3_TERRITORIES_JSON,
                TEST_PLAYERS).getTerritories();
        List<Territory> path = getTestPathThreeTerritories(territories);
        List<Territory> reversePath = getReverseTestPathThreeTerritories(territories);
        Unit germanTank = new Unit(null, GERMANY, TANK, null, 0, null);
        Unit russianTank = new Unit(null, USSR, TANK, null, 0, null);

        assertTrue(isValidTankBlitz(path, germanTank));
        assertTrue(isValidTankBlitz(reversePath, germanTank));
        territories.get("B").addUnit(russianTank);
        assertFalse(isValidTankBlitz(path, germanTank));
        assertFalse(isValidTankBlitz(reversePath, germanTank));
    }

    @Test
    public void testIsValidAmphibiousAssault() throws IOException {

        Map<String, Territory> territories = BoardBuilder.testBuild(
            USSR_AMPHIBIOUS_ASSAULT,
            TEST_PLAYERS).getTerritories();

        List<Territory> path = createPath(territories, "A", "B");
    }

    private List<Territory> createPath(Map<String, Territory> territories, String ... territoryNames) {
        List<Territory> path = new ArrayList<>();
        for(String territoryName : territoryNames) {
            path.add(territories.get(territoryName));
        }
        return path;
    }

    private List<Territory> getReverseTestPathThreeTerritories(Map<String, Territory> territoryMap) {
        return new ArrayList<Territory>() {{
            add(territoryMap.get("C"));
            add(territoryMap.get("B"));
            add(territoryMap.get("A"));
        }};
    }

    private List<Territory> getTestPathThreeTerritories(Map<String, Territory> territoryMap) {
        return new ArrayList<Territory>() {{
            add(territoryMap.get("A"));
            add(territoryMap.get("B"));
            add(territoryMap.get("C"));
        }};
    }
}
