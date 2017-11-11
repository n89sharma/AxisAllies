package axisalliestests;

import axisallies.board.Territory;
import axisallies.units.Unit;
import org.junit.jupiter.api.Test;

import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;
import static axisallies.nations.NationType.GERMANY;
import static axisallies.nations.NationType.JAPAN;
import static axisallies.nations.NationType.USA;
import static axisallies.units.UnitType.TANK;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TerritoryTest {

    @Test
    public void testIsSea() {
        Territory sea = new Territory(null, null, 0, null, null, SEA, null);
        assertTrue(sea.isSea());
        Territory land = new Territory(null, null, 0, null, null, LAND, null);
        assertFalse(land.isSea());
    }

    @Test
    public void testIsLand() {
        Territory sea = new Territory(null, null, 0, null, null, SEA, null);
        assertFalse(sea.isLand());
        Territory land = new Territory(null, null, 0, null, null, LAND, null);
        assertTrue(land.isLand());
    }
}
