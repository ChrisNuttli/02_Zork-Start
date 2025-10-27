package ch.bbw.zork;

import ch.bbw.zork.furniture.Door;
import ch.bbw.zork.rooms.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class House {
    public static ArrayList<Room> rooms;
    private ArrayList<Door> doors;

    public House() {
        this.doors = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.rooms.add(new FrontYard());
        this.rooms.add(new Attic());
        this.rooms.add(new Basement());
        this.rooms.add(new Bathroom());
        this.rooms.add(new Bedroom());
        this.rooms.add(new Cellar());
        this.rooms.add(new DiningRoom());
        this.rooms.add(new FrontYard());
        this.rooms.add(new Kitchen());
        this.rooms.add(new Livingroom());
        this.rooms.add(new Office());

        for (Room room : rooms) {
            while (room.countExits() < room.getMaxDoorCount()) {
                Random random =  new Random();
                Direction dir;
                boolean found = false;
                do {
                    dir = Direction.values()[random.nextInt(4)];
                    for (Room neighbor : rooms) {
                        if (room.exitExists(dir) || room == neighbor) {continue;}
                        found = room.addExit(dir, neighbor);
                    }
                } while (!found);
            }
        }

        for (Room room : rooms) {
            if (room.countExits() == 0) {
                throw new RuntimeException("Not all rooms have at least one valid neighbor");
            }
        }

        System.out.println("Rooms have at least one valid neighbor");
    }
}
