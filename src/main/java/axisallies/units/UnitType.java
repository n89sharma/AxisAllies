package axisallies.units;

public enum UnitType {
    //TYPE,             PRODUCTION_COST, MOVEMENT_RANGE, ATTACK_STRENGTH, DEFENCE_STRENGTH
    INFANTRY            ( 3, 1, 2, 1),
    ARTILLERY           ( 4, 2, 2, 1),
    TANK                ( 6, 3, 3, 2),
    FIGHTER             (10, 3, 4, 4),
    BOMBER              (12, 4, 4, 2),
    AA_GUN              ( 5, 0, 0, 1),
    DESTROYER           ( 8, 2, 2, 2),
    TRANSPORT           ( 7, 0, 0, 2),
    CRUISER             (12, 3, 3, 2),
    AIRCRAFT_CARRIER    (14, 1, 2, 2),
    SUBMARINE           ( 6, 2, 1, 2),
    BATTLESHIP          (20, 4, 4, 2);

    private final int productionCost;
    private final int attackStrength;
    private final int defenseStrength;
    private final int movementRange;

    UnitType(int productionCost, int attackStrength, int defenseStrength, int movementRange){
        this.productionCost = productionCost;
        this.attackStrength = attackStrength;
        this.defenseStrength = defenseStrength;
        this.movementRange = movementRange;
    }
}