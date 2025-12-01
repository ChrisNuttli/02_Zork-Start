package ch.bbw.zork;


import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.enums.ItemData;
import ch.bbw.zork.enums.RoomData;

import static ch.bbw.zork.enums.ItemData.*;
import static ch.bbw.zork.enums.FurnitureData.*;
import static ch.bbw.zork.enums.RoomData.*;

public final class Constants {
    private Constants() {}

    public static final int MAP_HEIGHT = 10;
    public static final int MAP_WIDTH = 10;

    public static final int ROOM_HEIGHT = 15;
    public static final int ROOM_WIDTH = 25;

    public static final int MAX_GEN_ITERATIONS = 10000;
    public static final int MAX_CORRIDORS = 10;
    public static final int HIDDEN_ITEMS_COUNT = 3;
    public static final int TIME_NOTE_COUNT = 5;
    public static final int LOCKED_DOORS_COUNT = 3;

    public static final int STARTING_TIME = 300;
    public static final int MOVE_TIME = 5;
    public static final int SCAN_TIME = 10;
    public static final int CHECK_TIME = 5;
    public static final int TAKE_TIME = 2;
    public static final int FETCH_TIME = 5;
    public static final int STASH_TIME = 5;
    public static final int DROP_TIME = 2;

    public static final char WALL_CHAR = '#';
    public static final char DOOR_OPEN_CHAR = ' ';
    public static final char DOOR_LOCKED_CHAR = 'X';
    public static final char FLOOR_CHAR = '.';
    public static final String PLAYER_CHAR = "\uD83E\uDD77";

    private static final String openDoorHorizontal = new String(new char[3]).replace('\0', DOOR_OPEN_CHAR);
    private static final String lockedDoorHorizontal = new String(new char[3]).replace('\0', DOOR_LOCKED_CHAR);
    private static final String halfDoorWallHorizontal = new String(new char[Math.floorDiv(ROOM_WIDTH - 3,2)]).replace('\0', WALL_CHAR);

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

    public static FurnitureData[] FURNITURE_DATA_LIST = new FurnitureData[]{
            TABLE,
            TOILET,
            BED,
            SOFA,
            CHAIR,
            DESK,
            WARDROBE,
            FRIDGE,
            STOVE,
            BATHTUB,
            SINK,
            SHOWER,
            MIRROR,
            NIGHTSTAND,
            LAMP,
            SHELF,
            BOOKCASE,
            CABINET,
            WASHING_MACHINE,
            DRYER,
            FREEZER,
            TOOLBOX,
            LADDER,
            BOX,
            BARREL,
            WINE_RACK,
            BENCH,
            COAT_RACK,
            SHOE_RACK,
            SIDEBOARD,
            GRILL,
            GARDEN_TABLE,
            GARDEN_CHAIR,
            FLOWER_POT,
            COFFEE_TABLE,
            TV_STAND,
            FILING_CABINET,
            COMPUTER,
    };

    public final static ItemData[] ITEM_DATA_LIST = new ItemData[]{
            KEY,
            CROWBAR,
            BACKPACK,
            FLASHLIGHT,
            LOCATION_NOTE,
            TIME_NOTE
    };
}
