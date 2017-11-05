package axisalliestests;

import axisallies.board.Territory;
import axisallies.units.Unit;
import org.junit.jupiter.api.Test;

import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;
import static axisallies.nations.NationType.*;
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

    @Test
    public void testIsHostileFriendlyTo() {
        Territory territory = new Territory(null, USA, 0, null, null, null, null);
        Unit unit = new Unit(null, GERMANY, null, null, 0, null);
        assertTrue(territory.isHostileTo(unit));
        assertFalse(territory.isFriendlyTo(unit));
        territory.setNationType(JAPAN);
        assertFalse(territory.isHostileTo(unit));
        assertTrue(territory.isFriendlyTo(unit));
    }
}
