package axisallies.nations;

public enum TeamType {

    AXIS, ALLIES;

    public boolean isHostileTo(TeamType teamType) {
        return !this.equals(teamType);
    }

    public boolean isFriendly(TeamType teamType) {
        return !isHostileTo(teamType);
    }
}
