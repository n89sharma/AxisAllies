package axisallies.units;

import axisallies.GameEntity;

public class IndustrialComplex extends GameEntity{

    private int health;
    private String territory;

    public IndustrialComplex() {
        super();
        this.health = 10;
    }

    public void setRegion(String territory) {
        this.territory = territory;
    }
}