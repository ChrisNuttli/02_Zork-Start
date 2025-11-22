package ch.bbw.zork;

import ch.bbw.zork.Rooms.*;

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
    public static final Class<? extends Room>[] ROOM_TYPES = new Class[]{
            Attic.class,
            Bathroom.class,
            Bedroom.class,
            Cellar.class,
            Corridor.class,
            DiningRoom.class,
            FrontYard.class,
            Kitchen.class,
            LivingRoom.class,
            Office.class
    };

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

//    public static final String EAST_WALL_WEST_WALL_EMPTY = WALL_CHAR + new String(new char[ROOM_WIDTH-2]).replace('\0', DOOR_LOCKED_CHAR) + WALL_CHAR;
//    public static final String EAST_WALL_WEST_WALL_PLAYER = WALL_CHAR + halfFloorPlayerLeft + PLAYER_CHAR + halfFloorPlayerRight + WALL_CHAR;
//    public static final String EAST_DOOR_OPEN_WEST_WALL_EMPTY = DOOR_OPEN_CHAR + new String(new char[ROOM_WIDTH-2]).replace('\0', DOOR_LOCKED_CHAR) + WALL_CHAR;
//    public static final String EAST_DOOR_OPEN_WEST_WALL_PLAYER = DOOR_OPEN_CHAR + halfFloorPlayerLeft + PLAYER_CHAR + halfFloorPlayerRight + WALL_CHAR;
//    public static final String EAST_DOOR_LOCKED_WEST_WALL_EMPTY = DOOR_LOCKED_CHAR + new String(new char[ROOM_WIDTH-2]).replace('\0', DOOR_LOCKED_CHAR) + WALL_CHAR;
//    public static final String EAST_DOOR_LOCKED_WEST_WALL_PLAYER = DOOR_LOCKED_CHAR + halfFloorPlayerLeft + PLAYER_CHAR + halfFloorPlayerRight + WALL_CHAR;
//
//    public static final String EAST_WALL_WEST_WALL_DOOR_OPEN = WALL_CHAR + new String(new char[ROOM_WIDTH-2]).replace('\0', DOOR_LOCKED_CHAR) + DOOR_OPEN_CHAR;
//    public static final String EAST_WALL_WEST_DOOR_OPEN_PLAYER = WALL_CHAR + halfFloorPlayerLeft + PLAYER_CHAR + halfFloorPlayerRight + DOOR_OPEN_CHAR;
//    public static final String EAST_DOOR_OPEN_WEST_DOOR_OPEN_EMPTY = DOOR_OPEN_CHAR + new String(new char[ROOM_WIDTH-2]).replace('\0', DOOR_LOCKED_CHAR) + DOOR_OPEN_CHAR;
//    public static final String EAST_DOOR_OPEN_WEST_DOOR_OPEN_PLAYER = DOOR_OPEN_CHAR + halfFloorPlayerLeft + PLAYER_CHAR + halfFloorPlayerRight + DOOR_OPEN_CHAR;
//    public static final String EAST_DOOR_LOCKED_WEST_DOOR_OPEN_EMPTY = DOOR_LOCKED_CHAR + new String(new char[ROOM_WIDTH-2]).replace('\0', DOOR_LOCKED_CHAR) + DOOR_OPEN_CHAR;
//    public static final String EAST_DOOR_LOCKED_WEST_DOOR_OPEN_PLAYER = DOOR_LOCKED_CHAR + halfFloorPlayerLeft + PLAYER_CHAR + halfFloorPlayerRight + DOOR_OPEN_CHAR;


//    public static String WALL_HORIZONTAL_PLAIN() {
//        return new String(new char[ROOM_WIDTH]).replace('\0', WALL_CHAR);
//    }

//    public static String WALL_HORIZONTAL_LOCKED_DOOR() {
//        char[] resultArray = WALL_HORIZONTAL_PLAIN().toCharArray();
//        int center = Math.floorDiv(resultArray.length, 2);
//        for  (int i = center-1; i <= center+1; i++) {
//            resultArray[i] = DOOR_LOCKED_CHAR;
//        }
//
//        return String.valueOf(resultArray);
//    }

//    public static String WALL_HORIZONTAL() {
//        return WALL_HORIZONTAL(WALL_CHAR);
//    }
//
//    public static String WALL_HORIZONTAL(char doorChar) {
//        char[] resultArray = new String(new char[ROOM_WIDTH]).replace('\0', WALL_CHAR).toCharArray();
//        int center = Math.floorDiv(resultArray.length, 2);
//        for  (int i = center-1; i <= center+1; i++) {
//            resultArray[i] = doorChar;
//        }
//
//        return String.valueOf(resultArray);
//    }
//
//    public static String[] WALL_VERTICAL() {
//        return WALL_VERTICAL(WALL_CHAR);
//    }
//
//    public static String[] WALL_VERTICAL(char doorChar) {
//        String[] resultArray = new String(new char[ROOM_HEIGHT-2]).replace('\0', WALL_CHAR).split("");
//        int center = Math.floorDiv(resultArray.length, 2);
//        for  (int i = center-1; i <= center+1; i++) {
//            resultArray[i] = resultArray[i].replace(WALL_CHAR, doorChar);
//        }
//
//        return resultArray;
//    }
}
