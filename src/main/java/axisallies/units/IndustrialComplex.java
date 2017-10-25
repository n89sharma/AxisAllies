package axisallies.units;

public class IndustrialComplex extends Unit {

    private int health;
    private String region;

    public IndustrialComplex() {
        this.health = 10;
    }

    public void setRegion(String region){
        this.region = region;
    }
}