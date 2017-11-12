package axisallies.gameplay;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Map;

import axisallies.units.Company;
import axisallies.units.Unit;
import lombok.ToString;

@ToString
public class BattleStrip {

    Map<Integer, Long> unitsPerAttackValue;
    Map<Integer, Long> unitsPerDefenceValue;

    public void createAttackerStack(Company company) {
        unitsPerAttackValue = company.getUnits().stream().collect(groupingBy(Unit::getAttack, counting()));
    }

    public void createDefenderStack(Company company) {
        unitsPerAttackValue = company.getUnits().stream().collect(groupingBy(Unit::getDefense, counting()));
    }

    public Map<Integer, Long> getUnitsPerAttackValue() {
        return this.unitsPerAttackValue;
    }

    public Map<Integer, Long> getUnitsPerDefenceValue() {
        return this.unitsPerDefenceValue;
    }

}