package ch.bbw.zork;

public enum Direction {
	NORTH(0), EAST(1), SOUTH(2), WEST(3);

    private final int value;
    private Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Direction getOpposite() {
        switch (this) {
            case NORTH: return SOUTH;
            case EAST: return WEST;
            case SOUTH: return NORTH;
            case WEST: return EAST;
            default: return this;
        }
    }
}
