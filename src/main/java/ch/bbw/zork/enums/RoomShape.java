package ch.bbw.zork.enums;

public enum RoomShape {
    DEAD_END(1,4),
    STRAIGHT(2,2),
    BEND(2,4),
    FORK(3,4),
    CROSS(4,1);

    private final int doors;
    private final int rotations;

    private RoomShape(int doors, int rotations) {
        this.doors = doors;
        this.rotations = rotations;
    }
}
