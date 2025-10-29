package ch.bbw.zork;

import ch.bbw.zork.rooms.*;

public class House {
    public static Room[][] roomGrid;
    private Map map;

    public House() {
        roomGrid = new Room[5][5];
        FrontYard frontYard = new FrontYard();
        Tuple<Integer, Integer> frontYardCoordinates = frontYard.generateCoordinates();
        roomGrid[frontYardCoordinates.first][frontYardCoordinates.second] = frontYard;

        instantiateRoom(new Attic());
        instantiateRoom(new Basement());
        instantiateRoom(new Bathroom());
        instantiateRoom(new Bedroom());
        instantiateRoom(new Cellar());
        instantiateRoom(new DiningRoom());
        instantiateRoom(new Kitchen());
        instantiateRoom(new Livingroom());
        instantiateRoom(new Office());

        System.out.println("Display Map");

        this.map = new Map(5, 5);

        map.renderMap();
        map.displayMap();
    }

    private void instantiateRoom(Room room) {
        Tuple<Integer, Integer> roomCoordinates = room.generateCoordinates();
        while (roomGrid[roomCoordinates.first][roomCoordinates.second] != null) {
            roomCoordinates = room.generateCoordinates();
        }

        roomGrid[roomCoordinates.first][roomCoordinates.second] = room;
    }
}
