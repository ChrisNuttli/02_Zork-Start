package ch.bbw.zork;

import ch.bbw.zork.rooms.Room;

public class Map {
    private int height;
    private int width;
    public static int tileHeight;
    public static int tileWidth;
    public final static Character horizontalWallChar = '#';
    public final static Character verticalWallChar = '#';
    public final static Character horizontalOpenDoorChar = '.';
    public final static Character verticalOpenDoorChar = '.';
    public final static Character horizontalLockedDoorChar = 'x';
    public final static Character verticalLockedDoorChar = 'x';

    private String[] mapString;

    public Map(int height, int width) {
        this(height, width, 9, 16);
    }

    public Map(int height, int width, int tileHeight, int tileWidth) {
        this.height = height;
        this.width = width;
        Map.tileHeight = tileHeight;
        Map.tileWidth = tileWidth;
    }

    public void renderMap() {
        String[] mapLines = new String[height * tileHeight];
        for (int lineNum = 0; lineNum < mapLines.length; lineNum++) {
            mapLines[lineNum] = "";
            int y = Math.floorDiv(lineNum, tileHeight);
            for (int x = 0; x < this.width; x++) {
                Room room = House.roomGrid[y][x];
                if (room == null) {
                    mapLines[lineNum] += String.format("%-" + tileWidth + "s", " ");
                }
                else {
                    String[] roomBlueprint = room.getBlueprint();
                    mapLines[lineNum] += roomBlueprint[lineNum % roomBlueprint.length];
//                    if ((lineNum % tileHeight == 0 && !room.hasExists(Direction.NORTH)) || (lineNum % tileHeight == tileHeight - 1 && !room.hasExists(Direction.SOUTH))) {
//                            mapLines[lineNum] += String.format("%-" + Math.floorDiv(tileWidth,2) + "s", wallChar).replace(" ", wallChar.toString());
//                    }
//                    else if (lineNum % tileHeight == 0 || lineNum % tileHeight == tileHeight - 1) {
//                        mapLines[lineNum] += String.format("%-" + tileWidth + "s", wallChar).replace(" ", wallChar.toString());
//                    }
//                    else {
//                        mapLines[lineNum] += String.format("%s%" + (tileWidth-1) + "s", wallChar, wallChar);
//                    }
                }
            }
        }

        this.mapString = mapLines;
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

    public void displayMap() {
        for (String line : this.mapString) {
            System.out.println(line);
        }
    }
}
