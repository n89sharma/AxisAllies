package axisallies.units.components;

import java.util.Comparator;

import axisallies.units.Unit;

public class Combat {

    private int attack;
    private int defense;
    private int health;
    private boolean successful = false;
    private int lastRoll;
    private int lastRequiredRoll;

    public void setData(int attack, int defense, int health) {
        this.attack = attack;
        this.defense = defense;
        this.health = health;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
    }

    public int getHealth() {
        return this.health;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return this.successful;
    }

    public void setLastRoll(int lastRoll) {
        this.lastRoll = lastRoll;
    }

    public int getLastRoll() {
        return this.lastRoll;
    }

    public void setLastRequiredRoll(int lastRequiredRoll) {
        this.lastRequiredRoll = lastRequiredRoll;
    }

    public int getLastRequiredRoll() {
        return this.lastRequiredRoll;
    }

    public static class AttackComparator implements Comparator<Unit> {

        @Override
        public int compare(Unit first, Unit second) {
            return first.getCombat().getAttack() - second.getCombat().getAttack();
        }
    }

    public static class DefenseComparator implements Comparator<Unit> {

        @Override
        public int compare(Unit first, Unit second) {
            return first.getCombat().getDefense() - second.getCombat().getDefense();
        }
    }
}