package ch.bbw.zork;


import ch.bbw.zork.enums.RoomData;

import static ch.bbw.zork.enums.RoomData.*;

public final class Constants {
    private Constants() {}

    public static final int MAP_HEIGHT = 10;
    public static final int MAP_WIDTH = 10;

    public static final int ROOM_HEIGHT = 15;
    public static final int ROOM_WIDTH = 25;

    public static final char WALL_CHAR = '#';
    public static final char DOOR_OPEN_CHAR = ' ';
    public static final char DOOR_LOCKED_CHAR = 'X';
    public static final char FLOOR_CHAR = '.';
    public static final String PLAYER_CHAR = "\uD83E\uDD77";

    private static final String openDoorHorizontal = new String(new char[3]).replace('\0', DOOR_OPEN_CHAR);
    private static final String lockedDoorHorizontal = new String(new char[3]).replace('\0', DOOR_LOCKED_CHAR);
    private static final String halfDoorWallHorizontal = new String(new char[Math.floorDiv(ROOM_WIDTH - 3,2)]).replace('\0', WALL_CHAR);
    private static final String halfFloorPlayerLeft = new String(new char[Math.floorDiv(ROOM_WIDTH-2, 2)]).replace('\0', ' ');
    private static final String halfFloorPlayerRight = new String(new char[Math.floorDiv(ROOM_WIDTH-2, 2)-1]).replace('\0',  ' ');

    public static final int ROOM_NAME_LINE = (int)Math.ceil((double)ROOM_HEIGHT/2);
    public static final int PLAYER_LINE = ROOM_NAME_LINE + 1;

    public static final String HORIZONTAL_WALL_PLAIN = new String(new char[ROOM_WIDTH]).replace('\0', WALL_CHAR);
    public static final String HORIZONTAL_WALL_OPEN_DOOR = halfDoorWallHorizontal + openDoorHorizontal + halfDoorWallHorizontal;
    public static final String HORIZONTAL_WALL_LOCKED_DOOR = halfDoorWallHorizontal + lockedDoorHorizontal + halfDoorWallHorizontal;

    public static String FLOOR_LINE(char west, char east) {
        return FLOOR_LINE(west, east, " ");
    }

    public static String FLOOR_LINE(char west, char east, String center) {
        if (center.length() % 2 == 0) {
            center += FLOOR_CHAR;
        }
        String floorHalf = new String(new char[Math.floorDiv((ROOM_WIDTH-2)-center.length(), 2)]).replace('\0', FLOOR_CHAR);
        return west + floorHalf + center + floorHalf + east;
    }

    public static RoomData[] ROOM_DATA_LIST = new RoomData[]{
            ATTIC,
            BASEMENT,
            BATHROOM,
            BEDROOM,
            CELLAR,
            DINING_ROOM,
            FRONT_YARD,
            KITCHEN,
            LIVING_ROOM,
            OFFICE,
    };
}
