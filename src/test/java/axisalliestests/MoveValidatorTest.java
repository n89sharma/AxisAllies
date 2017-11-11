package axisalliestests;

import axisallies.board.Board;
import axisallies.board.BoardBuilder;
import axisallies.units.Path;
import axisallies.units.Unit;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static axisallies.nations.NationType.GERMANY;
import static axisallies.units.Path.createPath;
import static axisallies.units.UnitType.*;
import static axisallies.validators.MoveValidator.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveValidatorTest {

    private static final String USSR_SEA_3_TERRITORIES_JSON = "ussr_sea_3_territories.json";
    private static final String TEST_PLAYERS = "sample_players.json";
    private static final String USSR_GERMANY_LAND_2_LINEAR_TERRITORIES_JSON = "ussr_germany_land_2_linear_territories.json";

    @Test
    public void testIsUnitAtPathBeginning() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_SEA_3_TERRITORIES_JSON, TEST_PLAYERS);
        Path path = createPath(board, "A", "B", "C");
        Unit submarineUnit = new Unit(SUBMARINE, GERMANY, board.get("A"));

        assertFalse(isUnitAtPathBeginning(path, submarineUnit));
        board.get("A").addUnit(submarineUnit);
        assertTrue(isUnitAtPathBeginning(path, submarineUnit));
    }

    @Test
    public void testIsPathWithinUnitRange() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_SEA_3_TERRITORIES_JSON, TEST_PLAYERS);
        Path path = createPath(board, "A", "B", "C");
        Unit submarineUnit = new Unit(SUBMARINE, GERMANY);
        Unit bomberUnit = new Unit(BOMBER, GERMANY);
        Unit exhaustedFighterUnit = new Unit(FIGHTER, GERMANY);
        exhaustedFighterUnit.setTravelledDistance(2);

        assertFalse(isPathWithinUnitRange(path, submarineUnit));
        assertTrue(isPathWithinUnitRange(path, bomberUnit));
        assertFalse(isPathWithinUnitRange(path, exhaustedFighterUnit));
    }


    @Test
    public void testIsHostileTerritoriesPresentBeforeDestination() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_GERMANY_LAND_2_LINEAR_TERRITORIES_JSON, TEST_PLAYERS);
        Path toGermanyPath = createPath(board, "A", "B");
        Path toRussiaPath = createPath(board, "B", "A");
        Unit germanTank = new Unit(TANK, GERMANY);

        assertTrue(isHostileTerritoryPresentBeforeDestination(toGermanyPath, germanTank));
        assertFalse(isHostileTerritoryPresentBeforeDestination(toRussiaPath, germanTank));
    }

    @Test
    public void testIsPathValidTerritoryTypeForUnit() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_SEA_3_TERRITORIES_JSON, TEST_PLAYERS);
        Path path = createPath(board, "A", "B");
        Unit infantryUnit = new Unit(INFANTRY, GERMANY);
        Unit submarineUnit = new Unit(SUBMARINE, GERMANY);

        assertFalse(isPathValidTerritoryTypeForUnit(path, infantryUnit));
        assertTrue(isPathValidTerritoryTypeForUnit(path, submarineUnit));
    }
}