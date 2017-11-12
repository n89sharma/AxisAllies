package axisalliestests;

import static axisallies.nations.NationType.GERMANY;
import static axisallies.units.Path.createPath;
import static axisallies.units.Unit.buildUnitOfNationAtTerritory;
import static axisallies.units.UnitType.INFANTRY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import axisallies.board.Board;
import axisallies.board.BoardBuilder;
import axisallies.units.Path;
import axisallies.units.Unit;
import axisallies.validators.CombatMoveValidator;

public class UnitTest {

    private Board board = BoardBuilder.testBuild();

    public UnitTest() throws IOException {
    }

    @Test
    void myFirstTest() {
        Path path = createPath(board, "A", "B", "C");
        Unit infantryUnit = buildUnitOfNationAtTerritory(INFANTRY, GERMANY, board.get("A"));


        assertTrue(CombatMoveValidator.isDestinationHostile(path, infantryUnit));
        assertFalse(CombatMoveValidator.isValidTankBlitz(path, infantryUnit));
    }


}