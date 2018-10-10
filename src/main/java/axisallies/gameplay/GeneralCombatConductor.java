package axisallies.gameplay;

import axisallies.GameResponse;
import axisallies.Pair;
import axisallies.units.Unit;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;

import static axisallies.Pair.integerPairs;
import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class GeneralCombatConductor {

    private Set<Unit> attackers;
    private Set<Unit> defenders;
    private Set<Unit> allUnits = new HashSet<>();
    private Map<Integer, Unit> allUnitIds;
    private List<CombatRoundData> combatRoundData = new ArrayList<>();
    private int currentCombatRound = 0;
    private static final Random random = new Random();
    private static final String DISPLAY_FORMAT_FULL_STATUS = "%1$-6s%2$-20s%3$-12s%4$-16s%5$-10s";
    private static final String DISPLAY_FORMAT_SUCCESSFUL = "%1$-6s%2$-20s%3$-12s";
    private static final String SUCCESSFUL_HIT = "[o]";
    private static final String UNSUCCESSFUL_HIT = "[ ]";
    private static final String ATTACK_STATUS_HEADER = format(DISPLAY_FORMAT_FULL_STATUS, "#", "Attacker", "UUID", "Roll Success", "Roll");
    private static final String DEFEND_STATUS_HEADER = format(DISPLAY_FORMAT_FULL_STATUS, "#", "Defender", "UUID", "Roll Success", "Roll");
    private static final String ATTACKER_HIT_ASSIGNMENT = "Attacker to assign %s hits.";
    private static final String DEFENDER_HIT_ASSIGNMENT = "Defender to assign %s hits.";
    private static final String HIT_ASSIGNMENT_FORMAT = "Assign hits in the form (<ID of unit>, <ID of unit>).";
    private static final Scanner scanner = new Scanner(System.in);

    public GeneralCombatConductor(Set<Unit> attackers, Set<Unit> defenders) {
        this.attackers = attackers;
        this.defenders = defenders;
        allUnits.addAll(attackers);
        allUnits.addAll(defenders);
        var unitList = new ArrayList<Unit>(allUnits);
        IntStream
            .rangeClosed(1, unitList.size())
            .boxed()
            .forEach(id -> unitList.get(id - 1).setLocalID(id));
        allUnitIds = allUnits.stream().collect(toMap(Unit::getLocalID, identity()));
    }

    public void conductGeneralCombat() {
        // submarine surprise strike.
        // round of combat.
        // attacker assigns hits.
        // defender assigns hits.
        // assign submarines hits first.
        // dead units are taken out.
        // did attacker or defender loose all units?
        // does the attacker want to retreat?
        // loop
        conductOneRoundOfCombat();

    }

    private void conductOneRoundOfCombat() {
        var successfulAttackers = rollForUnitsAndGetSuccessfulUnits(attackers, CombatantType.ATTACKER);
        var successfulDefenders = rollForUnitsAndGetSuccessfulUnits(defenders, CombatantType.DEFENDER);
        getCombatStatusForLastRound().forEach(System.out::println);
        System.out.println(format(ATTACKER_HIT_ASSIGNMENT, successfulDefenders.size()));
        System.out.println(HIT_ASSIGNMENT_FORMAT);
        var attackerHitAssignments = collectHitAssignmentsFromTheUserUntilCorrectResponse(CombatantType.ATTACKER);
        System.out.println(format(DEFENDER_HIT_ASSIGNMENT, successfulAttackers.size()));
        System.out.println(HIT_ASSIGNMENT_FORMAT);
        var defenderHitAssignments = collectHitAssignmentsFromTheUserUntilCorrectResponse(CombatantType.DEFENDER);
        combatRoundData.add(new CombatRoundData(
            successfulAttackers,
            successfulDefenders,
            attackerHitAssignments,
            defenderHitAssignments));
        currentCombatRound += 1;
    }

    private List<Pair<Integer, Integer>> collectHitAssignmentsFromTheUserUntilCorrectResponse(CombatantType combatantAssigningHit) {
        var hitAssignments = integerPairs(scanner.nextLine());
        var manualHitAssignmentResponse = manualHitAssignment(hitAssignments, currentCombatRound, combatantAssigningHit);
        while(!manualHitAssignmentResponse.isSuccessful()) {
            manualHitAssignmentResponse.getAllMessages().forEach(System.out::println);
            hitAssignments = integerPairs(scanner.nextLine());
            manualHitAssignmentResponse = manualHitAssignment(hitAssignments, currentCombatRound, combatantAssigningHit);
        }
        return hitAssignments;
    }

    private static Set<Unit> rollForUnitsAndGetSuccessfulUnits(
        Set<Unit> units,
        CombatantType combatantType) {

        Function<Unit, Integer> getRequiredRoll = unit -> unit.getCombat().getAttack();
        if (combatantType.equals(CombatantType.ATTACKER)) {
            getRequiredRoll = unit -> unit.getCombat().getAttack();
        } else if (combatantType.equals(CombatantType.DEFENDER)) {
            getRequiredRoll = unit -> unit.getCombat().getDefense();
        }
        for (var unit : units) {
            var roll = getRandomRoll();
            var requiredRoll = getRequiredRoll.apply(unit);
            unit.getCombat().setLastRequiredRoll(requiredRoll);
            unit.getCombat().setLastRoll(roll);
            unit.getCombat().setSuccessful(roll <= requiredRoll);
        }
        return units
            .stream()
            .filter(unit -> unit.getCombat().isSuccessful())
            .collect(toSet());
    }

    public GameResponse manualHitAssignment(
        List<Pair<Integer, Integer>> hitAssignments,
        int round,
        CombatantType combatantAssigningHits) {

        var gameResponse = new GameResponse();
        var assignedUnits = hitAssignments
            .stream()
            .map(Pair::getLeft)
            .map(id -> allUnitIds.get(id))
            .collect(toSet());
        var combatData = this.combatRoundData.get(round);
        Sets.SetView<Unit> unsuccessfulAssignedUnits;
        Sets.SetView<Unit> unassignedSuccessfulUnits;

        if (combatantAssigningHits.equals(CombatantType.DEFENDER)) {
            var successfulAttackers = combatData.successfulAttackers;
            unsuccessfulAssignedUnits = Sets.difference(assignedUnits, successfulAttackers);
            unassignedSuccessfulUnits = Sets.difference(successfulAttackers, assignedUnits);
        } else {
            var successfulDefenders = combatData.successfulDefenders;
            unsuccessfulAssignedUnits = Sets.difference(assignedUnits, successfulDefenders);
            unassignedSuccessfulUnits = Sets.difference(successfulDefenders, assignedUnits);
        }
        if (!unsuccessfulAssignedUnits.isEmpty()) {
            gameResponse.setUnsuccessful();
            gameResponse.addError(buildErrorForUnsuccessfulAssignedUnits(unsuccessfulAssignedUnits));

        }
        if (!unassignedSuccessfulUnits.isEmpty()) {
            gameResponse.setUnsuccessful();
            gameResponse.addError(buildErrorForUnassignedSuccessfulUnits(unassignedSuccessfulUnits));
        }
        return gameResponse;
    }

    private GameResponse.GameError buildErrorForUnsuccessfulAssignedUnits(Sets.SetView<Unit> unsuccessfulAssignedUnits) {
        var message = new ArrayList<String>();
        message.add("The following units were unsuccessful but hits were assigned for them:");
        message.addAll(getUnitDisplayData(unsuccessfulAssignedUnits, this::getUnitThreeColumnData));
        return new GameResponse.GameError(message);
    }

    private GameResponse.GameError buildErrorForUnassignedSuccessfulUnits(Sets.SetView<Unit> unassignedSuccessfulUnits) {
        var message = new ArrayList<String>();
        message.add("The following units were successful in combat round but hits were NOT assigned for them:");
        message.addAll(getUnitDisplayData(unassignedSuccessfulUnits, this::getUnitThreeColumnData));
        return new GameResponse.GameError(message);
    }

    public List<String> getCombatStatusForLastRound() {
        var lines = new ArrayList<String>();
        lines.add(ATTACK_STATUS_HEADER);
        lines.addAll(getUnitDisplayData(attackers, this::getUnitFourColumnData));
        lines.add("");
        lines.add(DEFEND_STATUS_HEADER);
        lines.addAll(getUnitDisplayData(defenders, this::getUnitFourColumnData));
        return lines;
    }

    public List<String> getSuccessfulAttackersForRound(int round) {
        return getUnitDisplayData(combatRoundData.get(round).successfulAttackers, this::getUnitThreeColumnData);
    }

    public List<String> getSuccessfulDefendersForRound(int round) {
        return getUnitDisplayData(combatRoundData.get(round).successfulDefenders, this::getUnitThreeColumnData);
    }

    private static List<String> getUnitDisplayData(Set<Unit> units, Function<Unit, String> formattingFunction) {
        return units
            .stream()
            .map(formattingFunction)
            .collect(toList());
    }

    private String getUnitThreeColumnData(Unit unit) {
        return format(DISPLAY_FORMAT_SUCCESSFUL, unit.getLocalID(), unit.getTypeDisplay(), unit.getTruncatedUuid());
    }

    private String getUnitFourColumnData(Unit unit) {
        return format(DISPLAY_FORMAT_FULL_STATUS,
            unit.getLocalID(),
            unit.getTypeDisplay(),
            unit.getTruncatedUuid(),
            getRollDisplay(unit.getCombat().isSuccessful()),
            format("(%s/%s)", unit.getCombat().getLastRoll(), unit.getCombat().getLastRequiredRoll()));
    }

    private static String getRollDisplay(boolean success) {
        if (success)
            return SUCCESSFUL_HIT;
        else
            return UNSUCCESSFUL_HIT;
    }

    private static int getRandomRoll() {
        return random.nextInt(6) + 1;
    }

    private enum CombatantType {
        ATTACKER, DEFENDER
    }

    private static class CombatRoundData {

        Set<Unit> successfulAttackers;
        Set<Unit> successfulDefenders;
        List<Pair<Integer, Integer>> attackerHitAssignment;
        List<Pair<Integer, Integer>> defenderHitAssignment;


        private CombatRoundData(
            Set<Unit> successfulAttackers,
            Set<Unit> successfulDefenders,
            List<Pair<Integer, Integer>> attackerHitAssignment,
            List<Pair<Integer, Integer>> defenderHitAssignment) {

            this.successfulAttackers = Set.copyOf(successfulAttackers);
            this.successfulDefenders = Set.copyOf(successfulDefenders);
            this.attackerHitAssignment = attackerHitAssignment;
            this.defenderHitAssignment = defenderHitAssignment;
        }
    }
}