package axisalliestests.board;

import axisallies.board.Territory;
import axisallies.board.TerritoryType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TerritoryTest {

    @Test
    public void generalTest() throws InterruptedException {
//        System.out.println(String.format("%1$-20s%2$-12s%3$-14s", "Destroyer", "203dd4be", "[o]"));
//        while (true) {
//            var random = UUID.randomUUID();
//            System.out.println(random.toString().split("-")[0]);
//            System.out.println(random);
//            Thread.sleep(500);
//        }
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
