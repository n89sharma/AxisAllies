package axisallies.gameplay;

import axisallies.GameResponse;
import axisallies.Pair;
import axisallies.units.Unit;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;

import static axisallies.GameResponse.successfulGameResponseWithPayload;
import static axisallies.GameResponse.unsuccessfulGameResponseWithPayloadAndMessage;
import static axisallies.Pair.integerPairs;
import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

//TODO: sort the units before printing them. Sort by type,name lowest attack first.
public class GeneralCombatConductor {

    private Set<Unit> attackers;
    private Set<Unit> defenders;
    private Set<Unit> allUnits = new HashSet<>();
    private Map<Integer, Unit> allUnitIds;
    private List<CombatRoundData> combatRoundDataList = new ArrayList<>();
    private int currentCombatRound = 0;
    private static final Random random = new Random();
    private static final String DISPLAY_FORMAT_FULL_STATUS = "%1$-6s%2$-20s%3$-12s%4$-16s%5$-10s";
    private static final String DISPLAY_FORMAT_SUCCESSFUL = "%1$-6s%2$-20s%3$-12s";
    private static final String SUCCESSFUL_HIT = "[o]";
    private static final String UNSUCCESSFUL_HIT = "[ ]";
    private static final String ATTACK_STATUS_HEADER = format(DISPLAY_FORMAT_FULL_STATUS, "#", "Attacker", "UUID",
        "Roll Success", "Roll");
    private static final String DEFEND_STATUS_HEADER = format(DISPLAY_FORMAT_FULL_STATUS, "#", "Defender", "UUID",
        "Roll Success", "Roll");
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
        IntStream.rangeClosed(1, unitList.size()).boxed().forEach(id -> unitList.get(id - 1).setLocalID(id));
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
        var successfulAttackers = rollAndGetSuccessfulUnits(attackers, CombatantType.ATTACKER);
        var successfulDefenders = rollAndGetSuccessfulUnits(defenders, CombatantType.DEFENDER);
        getCombatStatusForLastRound().forEach(System.out::println);
        System.out.println(format(ATTACKER_HIT_ASSIGNMENT, successfulDefenders.size()));
        System.out.println(HIT_ASSIGNMENT_FORMAT);
        var attackerHitAssignments = collectCorrectHitAssignmentsFromUser(successfulAttackers);
        System.out.println(format(DEFENDER_HIT_ASSIGNMENT, successfulAttackers.size()));
        System.out.println(HIT_ASSIGNMENT_FORMAT);
        var defenderHitAssignments = collectCorrectHitAssignmentsFromUser(successfulDefenders);
        combatRoundDataList.add(new CombatRoundData(
            successfulAttackers,
            successfulDefenders,
            attackerHitAssignments,
            defenderHitAssignments));
        currentCombatRound += 1;
    }

    private static Set<Unit> rollAndGetSuccessfulUnits(Set<Unit> units, CombatantType combatantType) {
        Function<Unit, Integer> getRequiredRoll = combatantType.equals(CombatantType.ATTACKER)
            ? unit -> unit.getCombat().getAttack()
            : unit -> unit.getCombat().getDefense();
        for (var unit : units) {
            var roll = getRandomRoll();
            var requiredRoll = getRequiredRoll.apply(unit);
            unit.getCombat().setLastRequiredRoll(requiredRoll);
            unit.getCombat().setLastRoll(roll);
            unit.getCombat().setSuccessful(roll <= requiredRoll);
        }
        return units.stream().filter(unit -> unit.getCombat().isSuccessful()).collect(toSet());
    }

    private List<Pair<Integer, Integer>> collectCorrectHitAssignmentsFromUser(Set<Unit> successfulUnits) {
        GameResponse<List<Pair<Integer, Integer>>> validationResponse;
        do {
            validationResponse = integerPairs(scanner.nextLine()).map(hitAssignments -> validateHitAssignment(hitAssignments, successfulUnits));
            validationResponse.getAllMessages().forEach(System.out::println);
        } while (!validationResponse.isSuccessful());
        return validationResponse.getPayload();
    }

    //TODO: cannot assign two hits unless the unit is a battleship.
    private GameResponse<List<Pair<Integer, Integer>>> validateHitAssignment(
        List<Pair<Integer, Integer>> hitAssignments,
        Set<Unit> successfulUnits) {

        var assignedUnits = hitAssignments
            .stream()
            .map(Pair::getLeft)
            .map(id -> this.allUnitIds.get(id))
            .filter(Objects::nonNull)
            .collect(toSet());
        var unsuccessfulAssignedUnits = Sets.difference(assignedUnits, successfulUnits);
        var unassignedSuccessfulUnits = Sets.difference(successfulUnits, assignedUnits);
        if (!unsuccessfulAssignedUnits.isEmpty() || !unassignedSuccessfulUnits.isEmpty()) {
            var messages = new ArrayList<List<String>>();
            messages.add(buildMessageForUnsuccessfulAssignedUnits(unsuccessfulAssignedUnits));
            messages.add(buildMessageForUnassignedSuccessfulUnits(unassignedSuccessfulUnits));
            return unsuccessfulGameResponseWithPayloadAndMessage(messages, hitAssignments);
        }

        return successfulGameResponseWithPayload(hitAssignments);
    }

    private List<String> buildMessageForUnsuccessfulAssignedUnits(Sets.SetView<Unit> unsuccessfulAssignedUnits) {
        var message = new ArrayList<String>();
        //TODO: I want to take out this if statement.
        if (!unsuccessfulAssignedUnits.isEmpty()) {
            message.add("The following units were unsuccessful but hits were assigned for them:");
            message.addAll(getUnitDisplayData(unsuccessfulAssignedUnits, this::getUnitThreeColumnData));
        }
        return message;
    }

    private List<String> buildMessageForUnassignedSuccessfulUnits(Sets.SetView<Unit> unassignedSuccessfulUnits) {
        var message = new ArrayList<String>();
        //TODO: I want to take out this if statement.
        if (!unassignedSuccessfulUnits.isEmpty()) {
            message.add("The following units were successful in combat round but hits were NOT assigned for them:");
            message.addAll(getUnitDisplayData(unassignedSuccessfulUnits, this::getUnitThreeColumnData));
        }
        return message;
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
        return getUnitDisplayData(this.combatRoundDataList.get(round).successfulAttackers, this::getUnitThreeColumnData);
    }

    public List<String> getSuccessfulDefendersForRound(int round) {
        return getUnitDisplayData(this.combatRoundDataList.get(round).successfulDefenders, this::getUnitThreeColumnData);
    }

    private static List<String> getUnitDisplayData(Set<Unit> units, Function<Unit, String> formattingFunction) {
        return units.stream().map(formattingFunction).collect(toList());
    }

    private String getUnitThreeColumnData(Unit unit) {
        return format(DISPLAY_FORMAT_SUCCESSFUL, unit.getLocalID(), unit.getTypeDisplay(), unit.getTruncatedUuid());
    }

    private String getUnitFourColumnData(Unit unit) {
        return format(DISPLAY_FORMAT_FULL_STATUS, unit.getLocalID(), unit.getTypeDisplay(), unit.getTruncatedUuid(),
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