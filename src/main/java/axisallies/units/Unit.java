package axisallies.units;

import axisallies.GameEntity;
import axisallies.nations.NationType;
import axisallies.units.components.Combat;
import axisallies.units.components.Movement;

import java.util.Comparator;

public abstract class Unit extends GameEntity {

    private NationType nationType;
    private int productionCost;
    private Combat combat = new Combat();
    private Movement movement = new Movement();
    private boolean active = true;
    private String typeDisplay;
    private int localID;

    public abstract String getTypeDisplay();

    public abstract boolean can(Ability ability);

    public Unit() {

    }

    public Unit(NationType nationType, int productionCost) {
        this.nationType = nationType;
        this.productionCost = productionCost;
    }

    public NationType getNationType() {
        return nationType;
    }

    public void setNationType(NationType nationType) {
        this.nationType = nationType;
    }

    public void setLocalID(int id) {
        this.localID = id;
    }

    public int getLocalID() {
        return this.localID;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    public Combat getCombat() {
        return this.combat;
    }

    public Movement getMovement() {
        return this.movement;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

    public String getTruncatedUuid() {
        return getUuid().toString().split("-")[0];
    }

    public static class ProductionCostComparator implements Comparator<Unit> {

        @Override
        public int compare(Unit first, Unit second) {
            return first.getProductionCost() - second.getProductionCost();
        }
    }

}
