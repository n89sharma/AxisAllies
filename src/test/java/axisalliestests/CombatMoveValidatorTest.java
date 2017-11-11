package axisalliestests;

import static axisallies.units.Company.buildCompany;
import static axisallies.nations.NationType.GERMANY;
import static axisallies.nations.NationType.USSR;
import static axisallies.units.UnitType.SUBMARINE;
import static axisallies.units.UnitType.CRUISER;
import static axisallies.units.UnitType.TANK;
import static axisallies.units.UnitType.TRANSPORT;
import static axisallies.units.Path.createPath;
import static axisallies.validators.CombatMoveValidator.isDestinationHostile;
import static axisallies.validators.CombatMoveValidator.isValidTankBlitz;
import static axisallies.validators.CombatMoveValidator.isValidAmphibiousAssault;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

import axisallies.board.BoardBuilder;
import axisallies.board.Territory;
import axisallies.units.Company;
import axisallies.units.Path;
import axisallies.units.Unit;

public class CombatMoveValidatorTest {

    private static final String USSR_SEA_3_TERRITORIES_JSON = "ussr_sea_3_territories.json";
    private static final String GERMANY_USSR_LAND_3_TERRITORIES_JSON = "germany_ussr_land_3_territories.json";
    private static final String USSR_AMPHIBIOUS_ASSAULT  = "ussr_amphibious_assault.json";
    private static final String TEST_PLAYERS = "sample_players.json";

    @Test
    public void testIsCombatMove() throws IOException {

        Path path = createPath(
            BoardBuilder.testBuild(USSR_SEA_3_TERRITORIES_JSON, TEST_PLAYERS).getTerritories(), 
            "A", "B", "C");
        Unit germanSub = new Unit(SUBMARINE, GERMANY);
        Unit russianSub = new Unit(SUBMARINE, USSR);

        assertTrue(isDestinationHostile(path, germanSub));
        assertFalse(isDestinationHostile(path, russianSub));
    }

    @Test
    public void testIsValidBlitz() throws IOException {

        Map<String, Territory> territories = BoardBuilder.testBuild(
            GERMANY_USSR_LAND_3_TERRITORIES_JSON,
            TEST_PLAYERS).getTerritories();
        Path path = createPath(territories, "A", "B", "C");
        Path reversePath = createPath(territories, "C", "B", "A");
        Unit germanTank = new Unit(TANK, GERMANY);
        Unit russianTank = new Unit(TANK, USSR);

        assertTrue(isValidTankBlitz(path, germanTank));
        assertTrue(isValidTankBlitz(reversePath, germanTank));
        territories.get("B").addUnit(russianTank);
        assertFalse(isValidTankBlitz(path, germanTank));
        assertFalse(isValidTankBlitz(reversePath, germanTank));
    }

    @Test
    public void testIsValidAmphibiousAssault() throws IOException {

        Map<String, Territory> territories = BoardBuilder.testBuild(
            USSR_AMPHIBIOUS_ASSAULT,
            TEST_PLAYERS).getTerritories();

        territories.values().stream()
            .forEach(t -> t.populateNeighbours(territories));
        Path path = createPath(territories, "A", "B");

        Company landCompany = buildCompany(USSR, TANK, TANK);
        Company warshipCompany = buildCompany(USSR, CRUISER, CRUISER);
        Company amphibiousAssaultCompany = buildCompany(USSR, TRANSPORT, CRUISER);

        assertFalse(isValidAmphibiousAssault(path, landCompany));
        assertFalse(isValidAmphibiousAssault(path, warshipCompany));
        assertTrue(isValidAmphibiousAssault(path, amphibiousAssaultCompany));
    }
}
