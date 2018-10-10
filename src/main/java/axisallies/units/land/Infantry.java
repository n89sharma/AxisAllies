package axisallies.units.land;

import axisallies.nations.NationType;
import axisallies.units.Ability;
import axisallies.units.Unit;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.IntStream;

import static axisallies.units.Ability.*;
import static java.util.stream.Collectors.toSet;

public class Infantry extends Unit {

    private static final int productionCost = 3;
    private static final int health = 1;
    private static final int attack = 1;
    private static final int defense = 2;
    private static final int move = 1;
    private static final int attackBonus = 1;
    private static final String TYPE = "Infantry";

    private static EnumSet<Ability> abilities = EnumSet.of(
        MOVE_TO_LAND,
        MOVE_IN_COMBAT_MOVE,
        CONTAINABLE,
        SUPPORTING_UNIT_ATTACK_BONUS,
        ATTACK_LAND_UNITS,
        ATTACK_AIR_UNITS,
        CAPTURE_HOSTILE_TERRITORY,
        BE_ATTACKED_BY_AIR_UNITS);

    public static Set<Unit> getInfantryCompany(int numberOfUnits, NationType nationType) {
        return IntStream
            .rangeClosed(1, numberOfUnits)
            .boxed()
            .map(x -> infantry(nationType))
            .collect(toSet());
    }

    public static Infantry infantry(NationType nationType) {
        return new Infantry(nationType);
    }

    private Infantry(NationType nationType) {
        super(nationType, productionCost);
        this.getCombat().setData(attack, defense, health);
        this.getCombat().setAttackBonus(attackBonus);
        this.getMovement().setData(move);
    }

    public String getTypeDisplay() {
        return TYPE;
    }

    public boolean can(Ability ability) {
        return abilities.contains(ability);
    }
}