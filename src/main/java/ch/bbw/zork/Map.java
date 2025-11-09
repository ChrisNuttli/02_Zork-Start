package ch.bbw.zork;

import ch.bbw.zork.rooms.Room;

public class Map {
    private static int mapHeight;
    private static int mapWidth;
    public static int tileHeight;
    public static int tileWidth;
    public final static Character horizontalWallChar = '#';
    public final static Character verticalWallChar = '#';
    public final static Character horizontalOpenDoorChar = '.';
    public final static Character verticalOpenDoorChar = '.';
    public final static Character horizontalLockedDoorChar = 'x';
    public final static Character verticalLockedDoorChar = 'x';

    private static String[] mapString;

    public Map(int mapHeight, int mapWidth) {
        this(mapHeight, mapWidth, 9, 16);
    }

    public Map(int mapHeight, int mapWidth, int tileHeight, int tileWidth) {
        Map.mapHeight = mapHeight;
        Map.mapWidth = mapWidth;
        Map.tileHeight = tileHeight;
        Map.tileWidth = tileWidth;
    }

//    public void renderMap() {
//        House house = House.getInstance();
//        String[] mapLines = new String[mapHeight * tileHeight];
//        for (int lineNum = 0; lineNum < mapLines.length; lineNum++) {
//            mapLines[lineNum] = "";
//            int y = Math.floorDiv(lineNum, tileHeight);
//            for (int x = 0; x < this.mapWidth; x++) {
//                Room room = house.getRoom(x, y);
//                if (room == null) {
//                    mapLines[lineNum] += String.format("%-" + tileWidth + "s", " ");
//                }
//                else {
//                    String[] roomBlueprint = room.getBlueprint();
//                    mapLines[lineNum] += roomBlueprint[lineNum % roomBlueprint.length];
//                }
//            }
//        }
//
//        this.mapString = mapLines;
//    }

    /**
     * Determines which corner the given coordinates are located in, by returning a Tuple with the directions of
     * where the edge of the map is in relation to the coordinates.
     * The coordinates are defined as [y,x] start at [0,0] in the top left corner
     * and ends at [4,4] in the bottom right corner.
     * Ergo, coordinates [4,0] are in the bottom left corner and [0,4] are in the top right corner.
     * If the coordinates are not located in any corner, then null is returned.<br/>
     * Examples:<br/>
     * {@code Tuple<Direction,Direction> corner1 = Map.getCornerPlacement(new Tuple<Integer, Integer>(0,0));}<br/>
     * {@code System.out.println(corner1); // results in: (NORTH,WEST)}<br/><br/>
     * {@code Tuple<Direction,Direction> corner2 = Map.getCornerPlacement(new Tuple<Integer, Integer>(2,2));}<br/>
     * {@code System.out.println(corner2); // results in: null}
     * @param coordinates
     * @return The direction of the corner or null
     */
    public static Tuple<Direction, Direction> getCornerPlacement(Tuple<Integer, Integer> coordinates) {
        Direction vertical = null;
        Direction horizontal = null;
        if (coordinates.first == 0) {
            vertical = Direction.NORTH;
        }
        else if (coordinates.first == Map.mapHeight-1) {
            vertical = Direction.SOUTH;
        }

        if (coordinates.second == 0) {
            horizontal = Direction.WEST;
        }
        else if (coordinates.second == Map.mapWidth-1) {
            horizontal = Direction.EAST;
        }

        if (vertical == null || horizontal == null) {
            return null;
        }

        return new Tuple<>(vertical, horizontal);
    }

    /**
     * Determines The direction in which the map's edge lies in relation to the given coordinates.
     * If the coordinates are not at the edge or ar in a corner, then null is returned.
     * The coordinates are defined as [y,x] start at [0,0] in the top left corner
     * and ends at [4,4] in the bottom right corner.
     * Ergo, coordinates [4,1] is at the bottom edge and [3,4] is at the right edge.<br/>
     * Examples:<br/>
     * {@code Direction edge1 = Map.getEdgePlacement(new Tuple<Integer, Integer>(0,2)); }<br/>
     * {@code System.out.println(edge1); // results in: NORTH}<br/><br/>
     * {@code Direction edge2 = Map.getEdgePlacement(new Tuple<Integer, Integer>(1,2));}<br/>
     * {@code System.out.println(edge2); // results in: null}<br/><br/>
     * {@code Direction corner = Map.getEdgePlacement(new Tuple<Integer, Integer>(0,0));}<br/>
     * {@code System.out.println(corner); // results in: null}
     * @param coordinates
     * @return Direction or null
     */
    public static Direction getEdgePlacement(Tuple<Integer, Integer> coordinates) {
        if (isCorner(coordinates)) {
            return null;
        }
        else if (coordinates.first == 0) {
            return Direction.NORTH;
        }
        else if (coordinates.first == Map.mapHeight-1) {
            return Direction.SOUTH;
        }
        else if (coordinates.second == 0) {
            return Direction.WEST;
        }
        else if (coordinates.second == Map.mapWidth-1) {
            return Direction.EAST;
        }
        else {
            return null;
        }
    }

    public static boolean isCorner(Tuple<Integer, Integer> coordinate) {
        return (coordinate.first == 0 || coordinate.first == Map.mapHeight-1) && (coordinate.second == 0 || coordinate.second == Map.mapWidth-1);
    }

    public static boolean isEdge(Tuple<Integer, Integer> coordinate) {
        return coordinate.first == 0 || coordinate.first == Map.mapHeight-1 || coordinate.second == 0 || coordinate.second == Map.mapWidth-1;
    }

    public static String getCenteredText(String text, int length) {
        String leftString = String.format("%-" + (Math.floorDiv((tileWidth - text.length()), 2) + text.length()) + "s", text);

        return String.format("%" + (tileWidth-2) + "s", leftString);
    }

    private static String getWall(Character doorChar, int length) {
        String doorString = String.format("%" + Math.max(1, Math.floorDiv(length, 3)) + "s", doorChar).replace(' ', doorChar);
        int wallLengths = (int)Math.ceil((double)((length - doorString.length()) / 2));
        return String.format("%-" + (wallLengths + doorString.length()) + "s", doorString);
    }

    public static String getHorizontalWall() {
        return Map.getHorizontalWall(horizontalWallChar);
    }

    public static String getHorizontalWall(Character doorChar) {
        return String.format("%" + tileWidth + "s", getWall(doorChar, tileWidth)).replace(' ', horizontalWallChar);
    }

    public static String[] getVerticalWall() {
        return Map.getVerticalWall(verticalWallChar);
    }

    public static String[] getVerticalWall(Character doorChar) {
        return String.format("%" + tileWidth + "s", getWall(doorChar, tileHeight)).replace(' ', verticalWallChar).split("");
    }

    public static void displayMap() {
        for (String line : mapString) {
            System.out.println(line);
        }
    }

    public static int getMapHeight() {
        return mapHeight;
    }

    public static int getMapWidth() {
        return mapWidth;
    }
}
