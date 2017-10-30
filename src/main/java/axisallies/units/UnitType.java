package axisallies.units;

public enum UnitType {
    //TYPE,             PRODUCTION_COST, ATTACK_STRENGTH, DEFENCE_STRENGTH, MOVEMENT_RANGE
    INFANTRY            ( 3, 1, 2, 1, UnitTerrainType.LAND),
    ARTILLERY           ( 4, 2, 2, 1, UnitTerrainType.LAND),
    TANK                ( 6, 3, 3, 2, UnitTerrainType.LAND),
    FIGHTER             (10, 3, 4, 4, UnitTerrainType.AIR),
    BOMBER              (12, 4, 1, 6, UnitTerrainType.AIR),
    AA_GUN              ( 5, 0, 0, 1, UnitTerrainType.LAND),
    DESTROYER           ( 8, 2, 2, 2, UnitTerrainType.SEA),
    TRANSPORT           ( 7, 0, 0, 2, UnitTerrainType.SEA),
    CRUISER             (12, 3, 3, 2, UnitTerrainType.SEA),
    AIRCRAFT_CARRIER    (14, 1, 2, 2, UnitTerrainType.SEA),
    SUBMARINE           ( 6, 2, 1, 2, UnitTerrainType.SEA),
    BATTLESHIP          (20, 4, 4, 2, UnitTerrainType.SEA);

    private final int productionCost;
    private final int attackStrength;
    private final int defenseStrength;
    private final int movementRange;
    private final UnitTerrainType unitTerrainType;

    UnitType(int productionCost, int attackStrength, int defenseStrength, int movementRange, UnitTerrainType unitTerrainType){
        this.productionCost = productionCost;
        this.attackStrength = attackStrength;
        this.defenseStrength = defenseStrength;
        this.movementRange = movementRange;
        this.unitTerrainType = unitTerrainType;
    }

    public int getMovementRange() {
        return movementRange;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public UnitTerrainType getUnitTerrainType() {
        return unitTerrainType;
    }

    public enum UnitTerrainType {
        LAND, AIR, SEA;
    }
}