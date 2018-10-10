package axisallies.units.land;

import axisallies.nations.NationType;
import axisallies.units.Ability;
import axisallies.units.Unit;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.IntStream;

import static axisallies.units.Ability.*;
import static java.util.stream.Collectors.toSet;

public class Tank extends Unit {

    private static final int productionCost = 6;
    private static final int health = 1;
    private static final int attack = 3;
    private static final int defense = 3;
    private static final int move = 2;
    private static final String TYPE = "Tank";

    private static EnumSet<Ability> abilities = EnumSet.of(
        MOVE_TO_LAND,
        MOVE_IN_COMBAT_MOVE,
        MOVE_THROUGH_UNOCCUPIED_HOSTILE_TERRITORY,
        CONTAINABLE,
        ATTACK_LAND_UNITS,
        ATTACK_AIR_UNITS,
        CAPTURE_HOSTILE_TERRITORY,
        BE_ATTACKED_BY_AIR_UNITS);

    public static Set<Unit> getTankCompany(int numberOfUnits, NationType nationType) {
        return IntStream
            .rangeClosed(1, numberOfUnits)
            .boxed()
            .map(x -> tank(nationType))
            .collect(toSet());
    }

    public static Tank tank(NationType nationType) {
        return new Tank(nationType);
    }

    private Tank(NationType nationType) {
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