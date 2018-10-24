package axisallies.gameplay;

import axisallies.units.Unit;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static axisallies.Pair.integerPairs;
import static axisallies.nations.NationType.GERMANY;
import static axisallies.nations.NationType.USA;
import static axisallies.units.land.Infantry.getInfantryCompany;
import static axisallies.units.land.Infantry.infantry;
import static axisallies.units.land.Tank.getTankCompany;
import static axisallies.units.land.Tank.tank;

public class GeneralCombatTest {

    @Test
    public void testCombatStatusDisplay() {
        var attack = new HashSet<Unit>();
        attack.addAll(getTankCompany(5, USA));
        attack.addAll(getInfantryCompany(5, USA));

        var defend = Set.of(infantry(GERMANY), tank(GERMANY), tank(GERMANY), tank(GERMANY));
        var gc = new GeneralCombatConductor(attack, defend);
//        gc.conductGeneralCombat();
//        gc.conductGeneralCombat();
//        gc.getCombatStatusForLastRound().forEach(System.out::println);
//        System.out.println("-------------------------");
//        gc.getCombatStatusForLastRound().forEach(System.out::println);
//        System.out.println("-------------------------");
//        gc.getSuccessfulAttackersForRound(0).forEach(System.out::println);
//        gc.getSuccessfulUnitsForRound(1, GeneralCombatConductor.CombatantType.DEFENDER).forEach(System.out::println);

        System.out.println(integerPairs("1,5;4,3"));
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(input);
    }


}
