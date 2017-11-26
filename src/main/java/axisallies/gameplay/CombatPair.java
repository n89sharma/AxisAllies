package axisallies.gameplay;

import lombok.Getter;

@Getter
public class CombatPair {

    private Combatant striker;
    private Combatant fodder;

    public static CombatPair combatPairOf(Combatant striker, Combatant fodder) {
        return new CombatPair(striker, fodder);
    }

    private CombatPair(Combatant striker, Combatant fodder) {
        this.striker = striker;
        this.fodder = fodder;
    }
}
