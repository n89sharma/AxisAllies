package axisallies.units.components;

import axisallies.board.Territory;

public class Movement {

    private Territory currentLocation;
    private int range;
    private int travelledDistance;

    public void setData(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public int getTravelledDistance() {
        return travelledDistance;
    }

    public void incrementTravelledDistance(int delta) {
        this.travelledDistance = travelledDistance + delta;
    }

    public Territory getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Territory territory) {
        this.currentLocation = territory;
    }
}