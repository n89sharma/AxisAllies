package axisalliestests;

import axisallies.board.Board;
import axisallies.board.BoardBuilder;
import axisallies.board.Territory;
import axisallies.units.Path;
import axisallies.units.Unit;
import axisallies.validators.CombatMoveValidator;
import com.google.common.graph.MutableGraph;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static axisallies.nations.NationType.GERMANY;
import static axisallies.units.UnitType.INFANTRY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static axisallies.units.Path.createPath;

public class UnitTest {

    private Board board = BoardBuilder.testBuild();
    private Map<String, Territory> territories = board.getTerritories();
    private MutableGraph<Territory> graph = board.getGraph();

    public UnitTest() throws IOException {
    }

    @Test
    void myFirstTest() {
        Path path = createPath(territories, "A", "B", "C");
        Unit infantryUnit = new Unit(INFANTRY, GERMANY, territories.get("A"));


        assertTrue(CombatMoveValidator.isDestinationHostile(path, infantryUnit));
        assertFalse(CombatMoveValidator.isValidTankBlitz(path, infantryUnit));
    }


}