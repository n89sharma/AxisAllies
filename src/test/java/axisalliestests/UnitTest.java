package axisalliestests;

import axisallies.board.Board;
import axisallies.board.BoardBuilder;
import axisallies.board.Territory;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {

    private Board board = BoardBuilder.testBuild();
    private Map<String, Territory> territories = board.getTerritories();
    private MutableGraph<Territory> graph = board.getGraph();

    public UnitTest() throws IOException {
    }

    @Test
    void myFirstTest() {
        final List<Territory> path = new ArrayList<Territory>() {{
            add(territories.get("A"));
            add(territories.get("B"));
            add(territories.get("C"));
        }};
        Unit infantryUnit = new Unit(territories.get("A"), GERMANY, INFANTRY, "A");


        assertEquals(CombatMoveValidator.isCombatMove(path, infantryUnit), true);
        assertEquals(CombatMoveValidator.isValidBlitz(path, infantryUnit), false);
    }


}