package axisalliestests;

import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import axisallies.board.Territory;
import axisallies.board.TerritoryType;

public class TerritoryTest {

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
