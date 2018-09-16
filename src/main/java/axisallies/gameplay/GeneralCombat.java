package axisallies.gameplay;

import axisallies.units.Company;
import axisallies.units.UnitType;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static axisallies.gameplay.StrikerType.ATTACKER;
import static axisallies.gameplay.StrikerType.DEFENDER;
import static axisallies.units.UnitType.DESTROYER;
import static axisallies.units.UnitType.SUBMARINE;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@ToString
public class GeneralCombat {

    private List<Combatant> attackForce;
    private List<Combatant> defenceForce;
    private List<Combatant> attackSubmarineForce;
    private List<Combatant> defenceSubmarineForce;

    protected GeneralCombat(Company attackers, Company defenders) {
        attackForce = attackers
            .getUnits()
            .stream()
            .map(unit -> Combatant.of(unit, ATTACKER))
            .sorted(comparing(Combatant::getRequiredRoll))
            .collect(toList());
        defenceForce = defenders
            .getUnits()
            .stream()
            .map(unit -> Combatant.of(unit, DEFENDER))
            .sorted(comparing(Combatant::getRequiredRoll))
            .collect(toList());
        attackSubmarineForce = attackForce
            .stream()
            .filter(combatant -> combatant.getUnit().isType(SUBMARINE))
            .collect(toList());
        defenceSubmarineForce = defenceForce
            .stream()
            .filter(combatant -> combatant.getUnit().isType(SUBMARINE))
            .collect(toList());
    }

    public void conductGeneralCombat() {
        oneSidedSubmarineStrike(attackForce, attackSubmarineForce, defenceForce);
        oneSidedSubmarineStrike(defenceForce, defenceSubmarineForce, attackForce);
        removeCasualtiesFromPlay(attackForce);
        removeCasualtiesFromPlay(defenceForce);

        oneSidedStrike(attackForce, defenceForce);
        oneSidedStrike(defenceForce, attackForce);
        removeCasualtiesFromPlay(attackForce);
        removeCasualtiesFromPlay(defenceForce);
    }

    // the user can do a 
    // (1) surprise attack with the submarines; 
    // (2) submerge the submarines - except cannot submerge if enemey has destroyer; 
    // (3) continue with general combat?
    // exactly like combat round except instead of the entire attack force attacking only the submarines attack.
    private static void oneSidedSubmarineStrike(List<Combatant> strikeForce, List<Combatant> strikeSubmarineForce, List<Combatant> fodderForce) {
        boolean strikeSubmarinesExist = !strikeSubmarineForce.isEmpty();
        boolean submergeSubmarines = false;
        boolean attackWithSubmarines = true;
        boolean continueGeneralCombat = false;
        boolean isDestroyerPresent = fodderForce
            .stream()
            .anyMatch(combatant -> combatant.getUnit().isType(DESTROYER));

        if(strikeSubmarinesExist) {
            // check if enemy has a destroyer
            // remove from attack force and submarine force
            if(submergeSubmarines && !isDestroyerPresent) {
                strikeForce.removeAll(strikeSubmarineForce);
                strikeSubmarineForce = new ArrayList<>();
            }
            // remove from submarine force
            else if(continueGeneralCombat) {
                strikeSubmarineForce = new ArrayList<>();
            }
            else if(attackWithSubmarines) {
                strikeForce.removeAll(strikeSubmarineForce);
                oneSidedStrike(strikeSubmarineForce, fodderForce);
            }
        }
    }

    // 1. one sided strike
    //      1. get user input for die roll
    //      2. assign role
    //      3. get user input for hit assignment
    //      4. check hit assignment
    // 2. remove casualties

    private static void oneSidedStrike(List<Combatant> strikers, List<Combatant> fodders) {
        for (Combatant combatant : strikers) {
            System.out.print(combatant.getInputString());
            Integer roll = Integer.parseInt(System.console().readLine());
            combatant.setActualRoll(roll);
        }
        //if actual roll less than or equal to required roll then its a successful hit.
        List<Combatant> successfullStrikers = strikers
            .stream()
            .filter(Combatant::isSuccessHit)
            .collect(toList());
        //user decides which unit will take the hit
        for (Combatant striker : successfullStrikers) {
            Optional<Combatant> fodder;
            boolean isHitAssignmentValid = false;
            do {
                String casualtyUnitTypeInput = System.console().readLine();
                UnitType casualtyUnitType = UnitType.valueOf(casualtyUnitTypeInput.toUpperCase());
                fodder = getAliveCombatantFromUnitType(fodders, casualtyUnitType);
                if (fodder.isPresent()) {
                    isHitAssignmentValid = isHitAssignmentValid(strikers, striker, fodder.get());
                }
            } while (!fodder.isPresent() || !isHitAssignmentValid);
        }
    }

    private static Optional<Combatant> getAliveCombatantFromUnitType(List<Combatant> combatants, UnitType unitType) {
        return combatants
            .stream()
            .filter(combatant -> combatant.getUnit().getUnitType().equals(unitType))
            .filter(combatant -> !combatant.isCasualty())
            .findFirst();
    }

    private static boolean isHitAssignmentValid(List<Combatant> strikers, Combatant striker, Combatant fodder) {
        // if striker is air unit and fodder is submarine and no destroyer present in strikers then invalid.
        // if striker is submarine and fodder is air unit then invalid.
        boolean isDestroyerPresent = strikers
            .stream()
            .anyMatch(combatant -> combatant.getUnit().isType(DESTROYER));
        boolean isStrikerAirUnit = striker.getUnit().isAirUnit();
        boolean isFodderASubmarine = fodder.getUnit().isType(SUBMARINE);
        boolean isStrikerASubmarine = striker.getUnit().isType(SUBMARINE);
        boolean isFodderAirUnit = fodder.getUnit().isAirUnit();

        return (!(isStrikerAirUnit && isFodderASubmarine && !isDestroyerPresent) &&
            !(isStrikerASubmarine && isFodderAirUnit));
    }

    private static void removeCasualtiesFromPlay(List<Combatant> combatants) {
        List<Combatant> casualties = combatants
            .stream()
            .filter(Combatant::isCasualty)
            .collect(toList());
        combatants.removeAll(casualties);
    }
}