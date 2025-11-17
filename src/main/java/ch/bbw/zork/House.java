package ch.bbw.zork;

import ch.bbw.zork.Rooms.*;
import ch.bbw.zork.enums.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import static ch.bbw.zork.Constants.MAP_HEIGHT;
import static ch.bbw.zork.Constants.MAP_WIDTH;

public class House {
    public static HashSet<Room> roomList;
    public static MapTile[][] mapTiles;

    public House() {
        roomList = new HashSet<>();
        mapTiles = new MapTile[MAP_HEIGHT][MAP_WIDTH];

        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                mapTiles[y][x] = new MapTile(x, y);
            }
        }
    }

    public void generateHouse() {
        System.out.println("Please wait. Generating a new map...");

        generateNeighbors();

        System.out.println("Generation Done!");

        if (checkForInvalidPassages()) {
            System.out.println("Generated Map is not valid!");
        }
    }

    private void generateNeighbors() {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                MapTile tile = mapTiles[y][x];
                ArrayList<Class<? extends Room>> tileEntropy = tile.getEntropy();
                if (tileEntropy.size() > 1) {
                    tile.collapse();
                }
                else if (tileEntropy.size() == 1 && !tile.isInstantiated()) {
                    roomList.add(instantiateRoomObject(tileEntropy.get(0).getSimpleName()));
                    tile.setInstantiated(true);
                }
            }
        }
    }

    private boolean checkForInvalidPassages() {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                if (!mapTiles[y][x].isValid()) {
                    return false;
                }
            }
        }

        return true;
    }

    private HashSet<String> getRoomNames() {
        HashSet<String> roomNamesMap = new HashSet<>();
        for (Room room : roomList) {
            roomNamesMap.add(room.getName());
        }

        return roomNamesMap;
    }

    private Room instantiateRoomObject(String roomName) {
        switch(roomName) {
            case "Attic": return new Attic();
            case "Bathroom": return new Bathroom();
            case "Bedroom": return new Bedroom();
            case "Cellar": return new Cellar();
            case "Corridor": return new Corridor();
            case "DiningRoom": return new DiningRoom();
            case "FrontYard": return new FrontYard();
            case "Kitchen": return new Kitchen();
            case "LivingRoom": return new LivingRoom();
            case "Office": return new Office();
        }

        return new Corridor();
    }

    private boolean isAlreadyGenerated(String roomName) {
        if (Objects.equals(roomName, "Corridor")) return true;
        for (Room room : roomList) {
            if (room.getName().equals(roomName)) return true;
        }

        return false;
    }

    private Room getRoom(int[] coords) {
        return this.getRoom(coords[0], coords[1]);
    }

    public Room getRoom(int x, int y) {
        if (x < 0 || y < 0 || x >= MAP_WIDTH || y >= MAP_HEIGHT) {
            return null;
        }

        for (Room room : roomList) {
            if (room.getX() == x && room.getY() == y) {
                return room;
            }
        }

        return null;
    }

    private void addRoom(Room room) throws IllegalArgumentException {
        if (getRoom(room.getX(), room.getY()) != null) {
             throw new IllegalArgumentException("Room already exists!");
        }
        roomList.add(room);
    }

    public int[] getNeighborCoordinates(int[] coordinates, Direction dir) {
        int[] result = new int[2];

        switch(dir) {
            case NORTH:
                result[0] = coordinates[0];
                result[1] = coordinates[1]-1;
                break;
            case EAST:
                result[0] = coordinates[0]+1;
                result[1] = coordinates[1];
                break;
            case SOUTH:
                result[0] = coordinates[0];
                result[1] = coordinates[1]+1;
                break;
            case WEST:
                result[0] = coordinates[0]-1;
                result[1] = coordinates[1];
                break;
        }

        return result;
    }

    public static Room findRoomByCoordinates(int[] coordinates) {
        return findRoomByCoordinates(coordinates[0], coordinates[1]);
    }

    public static Room findRoomByCoordinates(int x, int y) {
        for (Room room : roomList) {
            if (room.getX() == x && room.getY() == y) return room;
        }

        return null;
    }
}
