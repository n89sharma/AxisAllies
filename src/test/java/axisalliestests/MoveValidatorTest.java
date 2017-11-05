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
import static axisallies.units.UnitType.*;
import static axisallies.validators.MoveValidator.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveValidatorTest {

    private static final String USSR_SEA_3_TERRITORIES_JSON = "ussr_sea_3_territories.json";
    private static final String USSR_LAND_3_TERRITORIES_JSON = "ussr_land_3_territories.json";
    private static final String TEST_PLAYERS = "sample_players.json";
    private static final String USSR_GERMANY_LAND_2_LINEAR_TERRITORIES_JSON = "ussr_germany_land_2_linear_territories.json";
    private static final String GERMANY_USSR_LAND_2_LINEAR_TERRITORIES_JSON = "germany_ussr_land_2_linear_territories.json";

    @Test
    public void testIsUnitAtPathBeginning() throws IOException {

        Map<String, Territory> territories = BoardBuilder.testBuild(USSR_SEA_3_TERRITORIES_JSON, TEST_PLAYERS).getTerritories();
        List<Territory> path = getTestPath(territories);
        Unit submarineUnit = new Unit(territories.get("A"), GERMANY, SUBMARINE, "A", 0, null);

        assertFalse(isUnitAtPathBeginning(path, submarineUnit));
        territories.get("A").addUnit(submarineUnit);
        assertTrue(isUnitAtPathBeginning(path, submarineUnit));
    }

    @Test
    public void testIsPathWithinUnitRange() throws IOException {

        Map<String, Territory> territories = BoardBuilder.testBuild(USSR_SEA_3_TERRITORIES_JSON, TEST_PLAYERS).getTerritories();
        List<Territory> path = getTestPath(territories);
        Unit submarineUnit = new Unit(null, GERMANY, SUBMARINE, null, 0, null);
        Unit bomberUnit = new Unit(null, GERMANY, BOMBER, null, 0, null);
        Unit exhaustedFighterUnit = new Unit(null, GERMANY, FIGHTER, null, 2, null);

        assertFalse(isPathWithinUnitRange(path, submarineUnit));
        assertTrue(isPathWithinUnitRange(path, bomberUnit));
        assertFalse(isPathWithinUnitRange(path, exhaustedFighterUnit));
    }


    @Test
    public void testIsHostileTerritoriesPresentBeforeDestination() throws IOException {

        List<Territory> toGermanyPath = getTestPathTwoTerritories(
                BoardBuilder.testBuild(
                        USSR_GERMANY_LAND_2_LINEAR_TERRITORIES_JSON,
                        TEST_PLAYERS).getTerritories());
        List<Territory> toRussiaPath = getTestPathTwoTerritories(
                BoardBuilder.testBuild(
                        GERMANY_USSR_LAND_2_LINEAR_TERRITORIES_JSON,
                        TEST_PLAYERS).getTerritories());
        Unit germanTank = new Unit(null, GERMANY, TANK, null, 0, null);

        assertTrue(isHostileTerritoryPresentBeforeDestination(toGermanyPath, germanTank));
        assertFalse(isHostileTerritoryPresentBeforeDestination(toRussiaPath, germanTank));
    }

    @Test
    public void testIsPathValidTerritoryTypeForUnit() throws IOException {

        Map<String, Territory> territories = BoardBuilder.testBuild(USSR_SEA_3_TERRITORIES_JSON, TEST_PLAYERS).getTerritories();
        List<Territory> path = getTestPath(territories);
        Unit infantryUnit = new Unit(null, GERMANY, INFANTRY, null, 0, null);
        Unit submarineUnit = new Unit(null, GERMANY, SUBMARINE, null, 0, null);

        assertFalse(isPathValidTerritoryTypeForUnit(path, infantryUnit));
        assertTrue(isPathValidTerritoryTypeForUnit(path, submarineUnit));
    }

    private List<Territory> getTestPath(Map<String, Territory> territoryMap) {
        return new ArrayList<Territory>() {{
            add(territoryMap.get("A"));
            add(territoryMap.get("B"));
            add(territoryMap.get("C"));
        }};
    }

    private List<Territory> getTestPathTwoTerritories(Map<String, Territory> territoryMap) {
        return new ArrayList<Territory>() {{
            add(territoryMap.get("A"));
            add(territoryMap.get("B"));
        }};
    }
}