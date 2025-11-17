package ch.bbw.zork;

import ch.bbw.zork.Rooms.*;
import ch.bbw.zork.enums.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import static ch.bbw.zork.Constants.MAP_HEIGHT;
import static ch.bbw.zork.Constants.MAP_WIDTH;

public class House {
    public static ArrayList<Room> roomList;

    public House() {
        roomList = new ArrayList<>();
    }

    public void generateHouse() {
        System.out.println("Please wait. Generating a new map...");

        for (int i = 0; i < 1000; i++) {
            try {
                generateNeighbors();
                break;
            }
            catch(Exception e) {
                roomList.forEach(room -> {
                    System.out.println(room.toString());
                });
            }
        }

        System.out.println("Generation Done!");
    }

    private void generateNeighbors() {
        roomList.clear();
        FrontYard frontYard = new FrontYard();
        roomList.add(frontYard);

        while (true) {
            ArrayList<Room> newRoomList = new ArrayList<>(roomList);
            for (Room room : newRoomList) {
                HashSet<Direction> missingPassages = room.getMissingNeighborDirections();
                if (missingPassages.isEmpty()) {continue;}
                for (Direction direction : missingPassages) {
                    System.out.println("Generating neighbor for " + room.getName() + " towards " + direction);
                    String neighborType = room.getRandomNeighbor(direction);
                    if (neighborType == null) {
                        throw new RuntimeException("Generation failed");
                    }
                    int[] neighborCoords = getNeighborCoordinates(new int[]{room.getX(), room.getY()}, direction);
                    Room neighborRoom = instantiateRoomObject(neighborType);
                    neighborRoom.setCoordinates(neighborCoords[0], neighborCoords[1]);
                    roomList.add(neighborRoom);
                }
            }

            if (roomList.size() == newRoomList.size() && !roomList.isEmpty()) {
                break;
            }
        }

        System.out.println(roomList.size() + " Rooms have been generated!");
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

        return findRoomByCoordinates(x, y);
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
