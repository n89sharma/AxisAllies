package axisallies.units;

import axisallies.board.TerritoryType;

import static axisallies.board.TerritoryType.*;

public enum UnitType {


    //TYPE,             PRODUCTION_COST, ATTACK_STRENGTH, DEFENCE_STRENGTH, MOVEMENT_RANGE, TERRITORY TYPE
    // @formatter:off
    INFANTRY        ( 3, 1, 2, 1, LAND),
    ARTILLERY       ( 4, 2, 2, 1, LAND),
    TANK            ( 6, 3, 3, 2, LAND),
    FIGHTER         (10, 3, 4, 4, AIR),
    BOMBER          (12, 4, 1, 6, AIR),
    AA_GUN          ( 5, 0, 0, 1, LAND),
    DESTROYER       ( 8, 2, 2, 2, SEA),
    TRANSPORT       ( 7, 0, 0, 2, SEA),
    CRUISER         (12, 3, 3, 2, SEA),
    AIRCRAFT_CARRIER(14, 1, 2, 2, SEA),
    SUBMARINE       ( 6, 2, 1, 2, SEA),
    BATTLESHIP      (20, 4, 4, 2, SEA);

// INFANTRY	        3	1	2		33%	67%
// ARTILLERY	    4	2	2		50%	50%
// TANK	            6	3	3		50%	50%
// FIGHTER	        10	3	4		30%	40%
// BOMBER	        12	4	1		33%	8%
// AA_GUN	        5	0	0		0%	0%
// DESTROYER	    8	2	2		25%	25%
// TRANSPORT	    7	0	0		0%	0%
// CRUISER	        12	3	3		25%	25%
// AIRCRAFT CARRIER	14	1	2		7%	14%
// SUBMARINE	    6	2	1		33%	17%
// BATTLESHIP	    20	4	4		20%	20%

    // @formatter:on

    private final int productionCost;
    private final int attackStrength;
    private final int defenseStrength;
    private final int movementRange;
    private final TerritoryType territoryType;

    UnitType(
        int productionCost,
        int attackStrength,
        int defenseStrength,
        int movementRange,
        TerritoryType territoryType) {

        this.productionCost = productionCost;
        this.attackStrength = attackStrength;
        this.defenseStrength = defenseStrength;
        this.movementRange = movementRange;
        this.territoryType = territoryType;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public int getDefenseStrength() {
        return defenseStrength;
    }

    public int getMovementRange() {
        return movementRange;
    }

    public TerritoryType getTerritoryType() {
        return territoryType;
    }
}