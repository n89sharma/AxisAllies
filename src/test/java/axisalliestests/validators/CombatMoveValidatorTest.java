package axisalliestests.validators;

import static axisallies.nations.NationType.GERMANY;
import static axisallies.nations.NationType.USSR;
import static axisallies.units.Company.buildCompany;
import static axisallies.board.Path.createPath;
import static axisallies.units.Unit.buildUnitOfNation;
import static axisallies.units.UnitType.AIRCRAFT_CARRIER;
import static axisallies.units.UnitType.BATTLESHIP;
import static axisallies.units.UnitType.BOMBER;
import static axisallies.units.UnitType.CRUISER;
import static axisallies.units.UnitType.DESTROYER;
import static axisallies.units.UnitType.FIGHTER;
import static axisallies.units.UnitType.INFANTRY;
import static axisallies.units.UnitType.SUBMARINE;
import static axisallies.units.UnitType.TANK;
import static axisallies.units.UnitType.TRANSPORT;
import static axisallies.validators.CombatMoveValidator.isDestinationHostile;
import static axisallies.validators.CombatMoveValidator.isValidAmphibiousAssault;
import static axisallies.validators.CombatMoveValidator.isValidAmphibiousAssaultOffloading;
import static axisallies.validators.CombatMoveValidator.isValidBomberMove;
import static axisallies.validators.CombatMoveValidator.isValidCombatRetreat;
import static axisallies.validators.CombatMoveValidator.isValidFighterMove;
import static axisallies.validators.CombatMoveValidator.isValidSubmarineMove;
import static axisallies.validators.CombatMoveValidator.isValidSubmarineTransportAssault;
import static axisallies.validators.CombatMoveValidator.isValidTankBlitz;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import axisallies.board.Board;
import axisallies.board.BoardBuilder;
import axisallies.board.Territory;
import axisallies.units.CarrierUnit;
import axisallies.units.Company;
import axisallies.board.Path;
import axisallies.units.Unit;

public class CombatMoveValidatorTest {

    private static final String USSR_SEA_3_TERRITORIES_JSON = "ussr_sea_3_territories.json";
    private static final String GERMANY_USSR_LAND_3_TERRITORIES_JSON = "germany_ussr_land_3_territories.json";
    private static final String USSR_AMPHIBIOUS_ASSAULT  = "ussr_amphibious_assault.json";
    private static final String TEST_PLAYERS = "sample_players.json";

    @Test
    public void testIsCombatMove() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_SEA_3_TERRITORIES_JSON, TEST_PLAYERS);

        Path path = createPath(board, "A", "B", "C");
        Unit germanSub = buildUnitOfNation(SUBMARINE, GERMANY);
        Unit russianSub = buildUnitOfNation(SUBMARINE, USSR);

        assertTrue(isDestinationHostile(path, germanSub));
        assertFalse(isDestinationHostile(path, russianSub));
    }

    @Test
    public void testIsValidBlitz() throws IOException {

        Board board = BoardBuilder.testBuild(GERMANY_USSR_LAND_3_TERRITORIES_JSON, TEST_PLAYERS);
        Path path = createPath(board, "A", "B", "C");
        Path reversePath = createPath(board, "C", "B", "A");
        Unit germanTank = buildUnitOfNation(TANK, GERMANY);

        assertTrue(isValidTankBlitz(path, germanTank));
        assertTrue(isValidTankBlitz(reversePath, germanTank));
        board.get("B").placeCompany(buildCompany(USSR, TANK));
        assertFalse(isValidTankBlitz(path, germanTank));
        assertFalse(isValidTankBlitz(reversePath, germanTank));
    }

    @Test
    public void testIsValidAmphibiousAssault() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_AMPHIBIOUS_ASSAULT, TEST_PLAYERS);
        Path path = createPath(board, "A", "B");

        Company landCompany = buildCompany(USSR, TANK, TANK);
        Company warshipCompany = buildCompany(USSR, CRUISER, CRUISER);
        Company amphibiousAssaultCompany = buildCompany(USSR, TRANSPORT, CRUISER);

        assertFalse(isValidAmphibiousAssault(path, landCompany));
        assertFalse(isValidAmphibiousAssault(path, warshipCompany));
        assertTrue(isValidAmphibiousAssault(path, amphibiousAssaultCompany));
    }

    @Test
    public void testIsValidCombatRetreat() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_AMPHIBIOUS_ASSAULT, TEST_PLAYERS);
        Path validPath = createPath(board, "B", "A");
        Path invalidPath = createPath(board, "A", "B");
        Company amphibiousAssaultCompany = buildCompany(USSR, TRANSPORT, CRUISER);

        assertTrue(isValidCombatRetreat(validPath, amphibiousAssaultCompany));
        assertFalse(isValidCombatRetreat(invalidPath, amphibiousAssaultCompany));
    }

    @Test
    public void testIsValidSubmarineTransportAssault() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_SEA_3_TERRITORIES_JSON, TEST_PLAYERS);
        Path path = createPath(board, "A", "B");
        board.get("B").placeCompany(buildCompany(GERMANY, CRUISER, BATTLESHIP));
        Unit germanSub = buildUnitOfNation(SUBMARINE, GERMANY);
        Unit germanTransport = buildUnitOfNation(TRANSPORT, GERMANY);
        Unit russianCruiser = buildUnitOfNation(CRUISER, USSR);


        assertFalse(isValidSubmarineTransportAssault(path, russianCruiser));

        board.get("B").addUnitToCompany(buildUnitOfNation(CRUISER, USSR));
        assertFalse(isValidSubmarineTransportAssault(path, russianCruiser));

        board.get("B").addUnitToCompany(germanSub);
        assertTrue(isValidSubmarineTransportAssault(path, russianCruiser));

        board.get("B").removeUnitFromCompany(germanSub);
        assertFalse(isValidSubmarineTransportAssault(path, russianCruiser));

        board.get("B").addUnitToCompany(germanTransport);
        assertTrue(isValidSubmarineTransportAssault(path, russianCruiser));
    }

    @Test
    public void testIsValidBomberMove() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_SEA_3_TERRITORIES_JSON, TEST_PLAYERS);
        Path path = createPath(board, "A", "B");
        Unit bomber = buildUnitOfNation(BOMBER, GERMANY);
        Unit fighter = buildUnitOfNation(FIGHTER, GERMANY);

        assertFalse(isValidBomberMove(path, fighter));
        assertTrue(isValidBomberMove(path, bomber));
        bomber.setTravelledDistance(4);
        assertFalse(isValidBomberMove(path, bomber));
        bomber.setTravelledDistance(3);
        assertTrue(isValidBomberMove(path, bomber));
    }

    @Test
    public void testIsValidFighterMove() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_AMPHIBIOUS_ASSAULT, TEST_PLAYERS);
        Path seaPath = createPath(board, "A", "B");

        Unit germanFighter = buildUnitOfNation(FIGHTER, GERMANY);
        Unit germanBomber = buildUnitOfNation(BOMBER, GERMANY);

        assertFalse(isValidFighterMove(seaPath, germanBomber));

        Path landPath = createPath(board, "C", "D");
        germanFighter.setTravelledDistance(2);
        assertFalse(isValidFighterMove(landPath, germanFighter));

        germanFighter.setTravelledDistance(1);
        assertTrue(isValidFighterMove(landPath, germanFighter));

        Path landToSeaPath = createPath(board, "C", "B");
        germanFighter.setTravelledDistance(2);
        assertTrue(isValidFighterMove(landToSeaPath, germanFighter));
    }

    @Test
    public void testIsValidAmphibiousAssaultOffloading() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_AMPHIBIOUS_ASSAULT, TEST_PLAYERS);
        Path seaPath = createPath(board, "B", "D");
        CarrierUnit russianTransport = (CarrierUnit) buildUnitOfNation(TRANSPORT, USSR);
        CarrierUnit russianAircraftCarrier = (CarrierUnit) buildUnitOfNation(AIRCRAFT_CARRIER, USSR);
        Unit germanCruiser = buildUnitOfNation(CRUISER, GERMANY);
        Unit germanSubmarine = buildUnitOfNation(SUBMARINE, GERMANY);
        Unit germanTransport = buildUnitOfNation(TRANSPORT, GERMANY);
        Territory landingZone = board.get("B");

        assertFalse(isValidAmphibiousAssaultOffloading(seaPath, russianAircraftCarrier));
        assertFalse(isValidAmphibiousAssaultOffloading(seaPath, russianTransport));

        russianTransport.addContainedUnit(buildUnitOfNation(INFANTRY, USSR));
        assertTrue(isValidAmphibiousAssaultOffloading(seaPath, russianTransport));

        landingZone.addUnitToCompany(germanCruiser);
        assertFalse(isValidAmphibiousAssaultOffloading(seaPath, russianTransport));

        landingZone.removeUnitFromCompany(germanCruiser);
        landingZone.addUnitToCompany(germanSubmarine);
        assertTrue(isValidAmphibiousAssaultOffloading(seaPath, russianTransport));

        landingZone.removeUnitFromCompany(germanSubmarine);
        landingZone.addUnitToCompany(germanTransport);
        assertTrue(isValidAmphibiousAssaultOffloading(seaPath, russianTransport));
    }

    @Test
    public void testIsValidSubmarineMove() throws IOException {

        Board board = BoardBuilder.testBuild(USSR_AMPHIBIOUS_ASSAULT, TEST_PLAYERS);
        Path seaPath = createPath(board, "A", "B");
        Unit russianSubmarine = buildUnitOfNation(SUBMARINE, USSR);
        Unit russianCruiser = buildUnitOfNation(CRUISER, USSR);
        Unit germanDestroyer = buildUnitOfNation(DESTROYER, GERMANY);
        Unit germanCruiser = buildUnitOfNation(CRUISER, GERMANY);
        Territory destination = board.get("B");

        assertFalse(isValidSubmarineMove(seaPath, russianCruiser));

        destination.addUnitToCompany(germanDestroyer);
        assertFalse(isValidSubmarineMove(seaPath, russianSubmarine));

        destination.removeUnitFromCompany(germanDestroyer);
        destination.addUnitToCompany(germanCruiser);
        assertTrue(isValidSubmarineMove(seaPath, russianSubmarine));
    }
}
