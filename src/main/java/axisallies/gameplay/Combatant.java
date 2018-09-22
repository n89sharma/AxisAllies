package axisallies.gameplay;

import axisallies.units.Unit;

import static axisallies.gameplay.StrikerType.ATTACKER;

public class Combatant {

    private Unit unit;
    private Integer requiredRoll;
    private Integer actualRoll;
    private boolean successHit;
    private boolean casualty;
    private StrikerType strikerType;

    public static Combatant of(Unit unit, StrikerType strikerType) {
        return new Combatant(unit, strikerType);
    }

    private Combatant(Unit unit, StrikerType combatantType) {
        this.unit = unit;
        this.requiredRoll = combatantType.equals(ATTACKER) ?
            unit.getAttack() :
            unit.getDefense();
    }

    public void setActualRoll(Integer actualRoll) {
        this.actualRoll = actualRoll;
        this.successHit = actualRoll <= requiredRoll;
    }

    public void setCasualty() {
        casualty = true;
    }

    public String getInputString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(unit.getUnitType().toString());
        stringBuilder.append("/");
        stringBuilder.append(requiredRoll);
        stringBuilder.append("/");
        stringBuilder.append(strikerType);
        stringBuilder.append(":");
        return stringBuilder.toString();
    }

    public Unit getUnit() {
        return unit;
    }

    public Integer getRequiredRoll() {
        return requiredRoll;
    }

    public Integer getActualRoll() {
        return actualRoll;
    }

    public boolean isSuccessHit() {
        return successHit;
    }

    public boolean isCasualty() {
        return casualty;
    }

    public StrikerType getStrikerType() {
        return strikerType;
    }
}
