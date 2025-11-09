package ch.bbw.zork;

public final class Constants {
    private Constants() {}

    public static final int MAP_HEIGHT = 5;
    public static final int MAP_WIDTH = 5;

    public static final int HALF_MAP_HEIGHT = Math.floorDiv(MAP_HEIGHT-1, 2);
    public static final int HALF_MAP_WIDTH = Math.floorDiv(MAP_WIDTH-1, 2);

    public static final int ATTIC_DOOR_SLOTS = 1;
    public static final int BASEMENT_DOOR_SLOTS = 1;
    public static final int BATHROOM_DOOR_SLOTS = 1;
    public static final int BEDROOM_DOOR_SLOTS = 2;
    public static final int CELLAR_DOOR_SLOTS = 1;
    public static final int CORRIDOR_DOOR_SLOTS = 4;
    public static final int DINING_ROOM_DOOR_SLOTS = 2;
    public static final int FRONT_YARD_DOOR_SLOTS = 1;
    public static final int KITCHEN_DOOR_SLOTS = 3;
    public static final int LIVING_ROOM_DOOR_SLOTS = 3;
    public static final int OFFICE_DOOR_SLOTS = 2;

    public static final RoomShape ATTIC_SHAPE = RoomShape.DEAD_END;
    public static final RoomShape BASEMENT_SHAPE = RoomShape.DEAD_END;
    public static final RoomShape BATHROOM_SHAPE = RoomShape.DEAD_END;
    public static final RoomShape BEDROOM_SHAPE = RoomShape.BEND;
    public static final RoomShape CELLAR_SHAPE = RoomShape.DEAD_END;
    public static final RoomShape CORRIDOR_SHAPE = RoomShape.CROSS;
    public static final RoomShape DINING_ROOM_SHAPE = RoomShape.STRAIGHT;
    public static final RoomShape FRONT_YARD_SHAPE = RoomShape.DEAD_END;
    public static final RoomShape KITCHEN_SHAPE = RoomShape.FORK;
    public static final RoomShape LIVING_ROOM_SHAPE = RoomShape.FORK;
    public static final RoomShape OFFICE_SHAPE = RoomShape.BEND;
}
