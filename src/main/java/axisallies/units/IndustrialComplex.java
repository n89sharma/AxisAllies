package axisallies.units;

public class IndustrialComplex extends Unit {

    private int health;
    private String territory;

    public IndustrialComplex() {
        this.health = 10;
    }

    public void setRegion(String territory){
        this.territory = territory;
    }
}