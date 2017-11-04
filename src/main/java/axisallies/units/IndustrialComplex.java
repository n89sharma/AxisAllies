package axisallies.units;

public class IndustrialComplex {

    private int health;
    private String territory;

    public IndustrialComplex() {
        super();
        this.health = 10;
    }

    public void setRegion(String territory){
        this.territory = territory;
    }
}