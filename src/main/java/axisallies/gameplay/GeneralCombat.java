package axisallies.gameplay;

import axisallies.units.Company;
import axisallies.units.UnitType;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static axisallies.gameplay.CombatPair.combatPairOf;
import static java.util.stream.Collectors.toList;

@ToString
public class GeneralCombat {

    private List<Combatant> attackForce;
    private List<Combatant> attackSubmarineForce;
    private List<Combatant> defenceForce;
    private List<Combatant> defenceSubmarineForce;
    private boolean attackSubmarineSubmerge;    //if not then surprise if no destroyer present
    private boolean defenceSubmarineSubmerge;

    protected GeneralCombat(Company attacker, Company defender) {

    }

    public static void conductGeneralCombat() {


    }

    private static void combatRound(List<Combatant> attackForce, List<Combatant> defenceForce) {

        oneSidedStrike(attackForce, defenceForce);
        oneSidedStrike(defenceForce, attackForce);

        removeCasualtiesFromPlay(attackForce);
        removeCasualtiesFromPlay(defenceForce);
    }

    private static void oneSidedStrike(List<Combatant> strikers, List<Combatant> fodders) {

        for (Combatant combatant : strikers) {
            System.out.print(combatant.getInputString());
            Integer roll = Integer.parseInt(System.console().readLine());
            combatant.setActualRoll(roll);
        }

        //if actual roll less than or equal to required roll then its a successful hit.
        List<Combatant> successfullStrikers = strikers.stream()
                .filter(Combatant::isSuccessHit).collect(toList());
        //user decides which unit will take the hit
        List<CombatPair> combatPairs = new ArrayList<>();

        for (Combatant striker : successfullStrikers) {
            Optional<Combatant> fodder;
            do {
                String casualtyUnitTypeInput = System.console().readLine();
                UnitType casualtyUnitType = UnitType.valueOf(casualtyUnitTypeInput);
                fodder = getCombatantFromUnitType(fodders, casualtyUnitType);
            } while (!fodder.isPresent());
            combatPairs.add(combatPairOf(striker, fodder.get()));
        }

        isHitAssignmentValid(combatPairs);
    }

    private static Optional<Combatant> getCombatantFromUnitType(List<Combatant> combatants, UnitType unitType) {
        return combatants.stream()
                .filter(combatant -> combatant.getUnit().getUnitType().equals(unitType))
                .findFirst();
    }

    private static boolean isHitAssignmentValid(List<CombatPair> combatPairs) {
        return true;
    }

    private static void removeCasualtiesFromPlay(List<Combatant> combatants) {

        List<Combatant> casualties = combatants.stream()
                .filter(Combatant::isCasualty)
                .collect(toList());
        combatants.removeAll(casualties);
    }
}