package ch.bbw.zork;

import ch.bbw.zork.rooms.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class House {
    private static House instance = null;
    private Room[][] roomGrid;
    private final int mapHeight = 5;
    private final int mapWidth = 5;
    public static Class<? extends Room>[] roomTypes = new Class[]{
            FrontYard.class,
            Basement.class,
            Bathroom.class,
            Bedroom.class,
            Cellar.class,
            DiningRoom.class,
            Kitchen.class,
            Livingroom.class,
            Office.class
    };

    private Map map;

    private House() {
        roomGrid = new Room[mapHeight][mapWidth];

//        Tuple<Integer, Integer> frontYardCoordinates = frontYard.generateCoordinates();
//        roomGrid[frontYardCoordinates.first][frontYardCoordinates.second] = frontYard;

//        instantiateRoom(new Attic());
//        instantiateRoom(new Basement());
//        instantiateRoom(new Bathroom());
//        instantiateRoom(new Bedroom());
//        instantiateRoom(new Cellar());
//        instantiateRoom(new DiningRoom());
//        instantiateRoom(new Kitchen());
//        instantiateRoom(new Livingroom());
//        instantiateRoom(new Office());

//        System.out.println("Display Map");
//
        map = new Map(mapHeight, mapWidth);
//
//        map.renderMap();
    }

    public static synchronized House getInstance() {
        if (instance == null) {
            instance = new House();
        }

        return instance;
    }

    public void generateHouse() {
        FrontYard frontYard = new FrontYard();
    }

//    private void instantiateRoom(Room room) {
//        Tuple<Integer, Integer> roomCoordinates = room.generateCoordinates();
//        while (roomGrid[roomCoordinates.first][roomCoordinates.second] != null) {
//            roomCoordinates = room.generateCoordinates();
//        }
//
//        roomGrid[roomCoordinates.first][roomCoordinates.second] = room;
//    }

    private void addDoors(Room room) {

    }

    public static int getMapHeight() {
        return Map.getMapHeight();
    }

    public static int getMapWidth() {
        return Map.getMapWidth();
    }

    public Room[][] getRoomGrid() {
        return roomGrid;
    }

    public void addRoom(Room room) {
        Tuple<Integer, Integer> coords = room.getCoordinates();
        this.roomGrid[coords.first][coords.second] = room;
    }

    public Room getRoom(int x, int y) {
        return roomGrid[y][x];
    }

    public Room getRoom(Tuple<Integer, Integer> coordinates) {
        return roomGrid[coordinates.first][coordinates.second];
    }

    public void renderMap() {
        this.map.renderMap();
    }

    public HashSet<Class<? extends Room>> getUnusedRoomTypes() {
        HashSet<Class<? extends Room>> unusedTypes = new HashSet<>(Arrays.asList(roomTypes));

        for (int y = 0; y < Map.getMapHeight(); y++) {
            for (int x = 0; x < Map.getMapWidth(); x++) {
                Room room = getRoom(x, y);
                if (room != null) {
                    unusedTypes.remove(room.getClass());
                }
            }
        }

        return unusedTypes;
    }
}
