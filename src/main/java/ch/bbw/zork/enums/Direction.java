package ch.bbw.zork.enums;

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

    public static Direction parse(String direction) {
        switch(direction.toLowerCase()) {
            case "north":
            case "up":
            case "n":
                return NORTH;
            case "east":
            case "right":
            case "e":
                return EAST;
            case "south":
            case "down":
            case "s":
                return SOUTH;
            case "west":
            case "left":
            case "w":
                return WEST;
        }

        throw new IllegalArgumentException();
    }
}
