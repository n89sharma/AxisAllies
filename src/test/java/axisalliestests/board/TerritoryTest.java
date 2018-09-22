package axisalliestests.board;

import axisallies.board.Path;
import axisallies.board.Territory;
import axisallies.board.TerritoryType;
import org.junit.jupiter.api.Test;

import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TerritoryTest {

    @Test
    public void testEquals() {
        Path pathA = new Path();
        Path pathB = new Path();
        boolean isEqual = pathA.equals(pathB);
    }

    @Test
    public void testIsSea() {
        Territory sea = createTerritory(SEA);
        assertTrue(sea.isSea());
        Territory land = createTerritory(LAND);
        assertFalse(land.isSea());
    }

    @Test
    public void testIsLand() {
        Territory sea = createTerritory(SEA);
        assertFalse(sea.isLand());
        Territory land = createTerritory(LAND);
        assertTrue(land.isLand());
    }

    private static Territory createTerritory(TerritoryType territoryType) {
        Territory territory = new Territory();
        territory.setTerritoryType(territoryType);
        return territory;
    }
}
