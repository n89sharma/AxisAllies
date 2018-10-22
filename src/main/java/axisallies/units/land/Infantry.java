package axisallies.units.land;

import static axisallies.units.Ability.ATTACK_AIR_UNITS;
import static axisallies.units.Ability.ATTACK_LAND_UNITS;
import static axisallies.units.Ability.BE_ATTACKED_BY_AIR_UNITS;
import static axisallies.units.Ability.CAPTURE_HOSTILE_TERRITORY;
import static axisallies.units.Ability.CONTAINABLE;
import static axisallies.units.Ability.MOVE_IN_COMBAT_MOVE;
import static axisallies.units.Ability.MOVE_TO_LAND;
import static axisallies.units.Ability.SUPPORTING_UNIT_ATTACK_BONUS;
import static java.util.stream.Collectors.toSet;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.IntStream;

import axisallies.nations.NationType;
import axisallies.units.Ability;
import axisallies.units.Unit;

public class Infantry extends Unit {

    private static final int productionCost = 3;
    private static final int health = 1;
    private static final int attack = 1;
    private static final int defense = 2;
    private static final int move = 1;
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
        this.getMovement().setData(move);
    }

    public String getTypeDisplay() {
        return TYPE;
    }

    public boolean can(Ability ability) {
        return abilities.contains(ability);
    }
}