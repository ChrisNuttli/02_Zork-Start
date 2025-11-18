package ch.bbw.zork;

import ch.bbw.zork.Rooms.*;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.NorthPassage;
import ch.bbw.zork.interfaces.SouthPassage;
import ch.bbw.zork.interfaces.WestPassage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import static ch.bbw.zork.Constants.MAP_HEIGHT;
import static ch.bbw.zork.Constants.MAP_WIDTH;

public class House {
    public static ArrayList<Room> roomList;
    public static ArrayList<Furniture> furnitureList;

    public House() {
        roomList = new ArrayList<>();
    }

    public void generateHouse() {
        System.out.println("Please wait. Generating a new map...");

        for (int i = 0; i < 1000; i++) {
            try {
                generateRooms();
                break;
            }
            catch(Exception e) {
                roomList.forEach(room -> {
                    System.out.println(room.toString());
                });
            }
        }

        generateDoors();
    }

    private void generateRooms() {
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

    private void generateDoors() {
        for (Room room : roomList) {
            if (room instanceof NorthPassage && ((NorthPassage)room).getPassageNorth() == null) {
                int[] coords = ((NorthPassage) room).getCoordinatesNorth();
                Room neighbor = getRoom(coords);
                Passage door = new Passage(Direction.NORTH, neighbor, Direction.SOUTH, room);
                ((SouthPassage)neighbor).setPassageSouth(door);
                ((NorthPassage) room).setPassageNorth(door);
            }

            if (room instanceof EastPassage && ((EastPassage)room).getPassageEast() == null) {
                int[] coords = ((EastPassage) room).getCoordinatesEast();
                Room neighbor = getRoom(coords);
                Passage door = new Passage(Direction.EAST, neighbor, Direction.WEST, room);
                ((WestPassage)neighbor).setPassageWest(door);
                ((EastPassage) room).setPassageEast(door);
            }

            if (room instanceof SouthPassage && ((SouthPassage)room).getPassageSouth() == null) {
                int[] coords = ((SouthPassage) room).getCoordinatesSouth();
                Room neighbor = getRoom(coords);
                Passage door = new Passage(Direction.SOUTH, neighbor, Direction.NORTH, room);
                ((NorthPassage)neighbor).setPassageNorth(door);
                ((SouthPassage) room).setPassageSouth(door);
            }

            if (room instanceof WestPassage && ((WestPassage)room).getPassageWest() == null) {
                int[] coords = ((WestPassage) room).getCoordinatesWest();
                Room neighbor = getRoom(coords);
                Passage door = new Passage(Direction.WEST, neighbor, Direction.EAST, room);
                ((EastPassage)neighbor).setPassageEast(door);
                ((WestPassage) room).setPassageWest(door);
            }
        }
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
