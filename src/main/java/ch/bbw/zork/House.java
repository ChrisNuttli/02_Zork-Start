package ch.bbw.zork;

import ch.bbw.zork.Rooms.FrontYard;

import java.util.HashSet;
import java.util.Random;

public class House {
    private HashSet<Room> map;


    public House() {
        map = new HashSet<>();
    }

    public void generateHouse() {
        // Front Yard
        int[] coords = new int[]{-1, -1};
        while (getRoom(coords[0], coords[1]) == null) {
            coords = FrontYard.getRandomCoordinates();
        }

        addRoom(new FrontYard(coords));


    }

    private Room getRoom(int[] coords) {
        return this.getRoom(coords[0], coords[1]);
    }

    public Room getRoom(int x, int y) {
        if (x < 0 || y < 0 || x >= Constants.MAP_WIDTH || y >= Constants.MAP_HEIGHT) {
            return null;
        }

        for (Room room : map) {
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
        map.add(room);
    }
}
