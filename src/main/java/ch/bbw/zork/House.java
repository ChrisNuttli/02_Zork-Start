package ch.bbw.zork;

import ch.bbw.zork.Rooms.*;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.NorthPassage;
import ch.bbw.zork.interfaces.SouthPassage;
import ch.bbw.zork.interfaces.WestPassage;

import java.util.*;

import static ch.bbw.zork.Constants.*;

public class House {
    public static ArrayList<Room> roomList;
    public static ArrayList<Furniture> furnitureList;
    private Room playerLocation;

    public House() {
        roomList = new ArrayList<>();
    }

    public void generateHouse() {
        System.out.println("Please wait. Generating a new map...");

        for (int i = 0; i < 10000; i++) {
            try {
                generateRooms();
                if (roomList.size() < ROOM_TYPES.length) {
                    throw new RuntimeException("Not all room types were used in the generation");
                }

                generateDoors();

                if (Zork2.DEBUG) {
                    System.out.printf("Successful generation in attempt nr. %s.\n", i);
                }
                break;
            }
            catch(Exception e) {
//                if (Zork2.DEBUG) {
//                    System.out.printf(getMap());
//                    System.out.println(i + " " + e.getMessage());
//                }
            }
        }

        if (Zork2.DEBUG) {
            System.out.printf(getMap());
            System.out.println("");
        }
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
                    int[] neighborCoords = getNeighborCoordinates(new int[]{room.getX(), room.getY()}, direction);
                    ArrayList<Class<? extends Room>> validNeighbors = findValidRoomTypes(neighborCoords[0], neighborCoords[1]);
                    int index = Game.random.nextInt(validNeighbors.size());
                    Room neighbor = instantiateRoomObject(validNeighbors.get(index));
                    if (neighbor == null) {
                        throw new RuntimeException("Generation failed");
                    }

                    neighbor.setCoordinates(neighborCoords[0], neighborCoords[1]);
                    roomList.add(neighbor);

//                    //if (Zork2.DEBUG) System.out.println("Generating neighbor for " + room.getName() + " towards " + direction);
//                    String neighborType = room.getRandomNeighbor(direction);
//                    if (neighborType == null) {
//                        throw new RuntimeException("Generation failed");
//                    }
//                    int[] neighborCoords = getNeighborCoordinates(new int[]{room.getX(), room.getY()}, direction);
//                    Room neighborRoom = instantiateRoomObject(neighborType);
//                    neighborRoom.setCoordinates(neighborCoords[0], neighborCoords[1]);
//                    roomList.add(neighborRoom);
                }
            }

            if (roomList.size() == newRoomList.size() && !roomList.isEmpty()) {
                break;
            }
        }

        this.playerLocation = frontYard;

//        if (Zork2.DEBUG) System.out.println(roomList.size() + " Rooms have been generated!");
    }

    private ArrayList<Class<? extends Room>> findValidRoomTypes(int x, int y) {
        ArrayList<Class<? extends Room>> result = new ArrayList<>(Arrays.asList(ROOM_TYPES));

        // check north
        Room northRoom = getRoom(x, y-1);
        Room eastRoom = getRoom(x+1, y);
        Room southRoom = getRoom(x, y+1);
        Room westRoom = getRoom(x-1, y);

        for (Class<? extends Room> roomType : ROOM_TYPES) {
            if (!result.contains(roomType) || getUsedRoomTypes().contains(roomType)) {
                continue;
            }

            if ((northRoom instanceof Attic && (roomType == Cellar.class || roomType == FrontYard.class))) {
                result.remove(roomType);
                continue;
            }

            List<?> interfaces = Arrays.asList(roomType.getInterfaces());
            if (interfaces.contains(NorthPassage.class) != northRoom instanceof SouthPassage) {
                result.remove(roomType);
                continue;
            }

            if (interfaces.contains(EastPassage.class) != eastRoom instanceof WestPassage) {
                result.remove(roomType);
                continue;
            }

            if (interfaces.contains(SouthPassage.class) != southRoom instanceof NorthPassage) {
                result.remove(roomType);
                continue;
            }

            if (interfaces.contains(WestPassage.class) != westRoom instanceof EastPassage) {
                result.remove(roomType);
            }
        }

        return result;
    }

    private Room generateRoom(int x, int y) {
        Room northRoom = getRoom(x, y-1);
        Room eastRoom = getRoom(x+1, y);
        Room southRoom = getRoom(x, y+1);
        Room westRoom = getRoom(x-1, y);

        ArrayList<Direction> validDirections = new ArrayList<>();
        ArrayList<Direction> mustDirections = new ArrayList<>();

        if (northRoom == null || northRoom instanceof SouthPassage) {
            validDirections.add(Direction.NORTH);
            if (northRoom instanceof SouthPassage) {
                mustDirections.add(Direction.NORTH);
            }
        }

        if (eastRoom == null || eastRoom instanceof WestPassage) {
            validDirections.add(Direction.EAST);
            if (eastRoom instanceof WestPassage) {
                mustDirections.add(Direction.EAST);
            }
        }

        if (southRoom == null || southRoom instanceof NorthPassage) {
            validDirections.add(Direction.SOUTH);
            if (southRoom instanceof NorthPassage) {
                mustDirections.add(Direction.SOUTH);
            }
        }

        if (westRoom == null || westRoom instanceof EastPassage) {
            validDirections.add(Direction.WEST);
            if (westRoom instanceof EastPassage) {
                mustDirections.add(Direction.WEST);
            }
        }

        if (validDirections.isEmpty()) {
            return null;
        }

        ArrayList<Class<? extends Room>> validRoomTypes = new ArrayList<>(Arrays.asList(ROOM_TYPES));

        for (Class<? extends Room> roomClass : ROOM_TYPES) {
            List<?> interfaces = Arrays.asList(roomClass.getInterfaces());
            if (
                (interfaces.contains(NorthPassage.class) && !validDirections.contains(Direction.NORTH)) ||
                (!interfaces.contains(NorthPassage.class) && mustDirections.contains(Direction.NORTH)) ||
                (interfaces.contains(EastPassage.class) && !validDirections.contains(Direction.EAST)) ||
                (!interfaces.contains(EastPassage.class) && mustDirections.contains(Direction.EAST)) ||
                (interfaces.contains(SouthPassage.class) && !validDirections.contains(Direction.SOUTH)) ||
                (!interfaces.contains(SouthPassage.class) && mustDirections.contains(Direction.SOUTH)) ||
                (interfaces.contains(WestPassage.class) && !validDirections.contains(Direction.WEST)) ||
                (!interfaces.contains(WestPassage.class) && mustDirections.contains(Direction.WEST))
            ) {
                validRoomTypes.remove(roomClass);
            }
        }

        if (validRoomTypes.isEmpty()) {
            return null;
        }

        int index = Game.random.nextInt(validRoomTypes.size());
        return instantiateRoomObject(validRoomTypes.get(index).getSimpleName());
    }

    private void generateDoors() {
        for (Room room : roomList) {
            try {
                if (room instanceof NorthPassage && ((NorthPassage)room).getPassageNorth() == null) {
                    int[] coords = ((NorthPassage) room).getCoordinatesNorth();
                    Room neighbor = getRoom(coords);
                    Transition door = new Transition(Direction.NORTH, neighbor, Direction.SOUTH, room);
                    ((SouthPassage)neighbor).setPassageSouth(door);
                    ((NorthPassage) room).setPassageNorth(door);
                }

                if (room instanceof EastPassage && ((EastPassage)room).getPassageEast() == null) {
                    int[] coords = ((EastPassage) room).getCoordinatesEast();
                    Room neighbor = getRoom(coords);
                    Transition door = new Transition(Direction.EAST, neighbor, Direction.WEST, room);
                    ((WestPassage)neighbor).setPassageWest(door);
                    ((EastPassage) room).setPassageEast(door);
                }

                if (room instanceof SouthPassage && ((SouthPassage)room).getPassageSouth() == null) {
                    int[] coords = ((SouthPassage) room).getCoordinatesSouth();
                    Room neighbor = getRoom(coords);
                    Transition door = new Transition(Direction.SOUTH, neighbor, Direction.NORTH, room);
                    ((NorthPassage)neighbor).setPassageNorth(door);
                    ((SouthPassage) room).setPassageSouth(door);
                }

                if (room instanceof WestPassage && ((WestPassage)room).getPassageWest() == null) {
                    int[] coords = ((WestPassage) room).getCoordinatesWest();
                    Room neighbor = getRoom(coords);
                    Transition door = new Transition(Direction.WEST, neighbor, Direction.EAST, room);
                    ((EastPassage)neighbor).setPassageEast(door);
                    ((WestPassage) room).setPassageWest(door);
                }
            }
            catch(Exception e) {
                System.err.println("Error while adding a door to " + room.getName());
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private Room instantiateRoomObject(Class<? extends Room> roomType) {
        return instantiateRoomObject(roomType.getSimpleName());
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

    public Room getPlayerLocation() {
        return playerLocation;
    }

    public void setPlayerLocation(Room playerLocation) {
        this.playerLocation = playerLocation;
    }

    private ArrayList<Class<? extends Room>> getUsedRoomTypes() {
        ArrayList<Class<? extends Room>> roomTypeList = new ArrayList<>();
        for (Room room : roomList) {
            roomTypeList.add(room.getClass());
        }

        return roomTypeList;
    }

    public String getMap() {
        String[] mapStringArray = new String[MAP_HEIGHT * ROOM_HEIGHT];
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int l = 0; l < ROOM_HEIGHT; l++) {
                mapStringArray[(y * ROOM_HEIGHT) + l] = "";
            }

            for (int x = 0; x < MAP_WIDTH; x++) {
                Room room = getRoom(x, y);
                for (int l = 0; l < ROOM_HEIGHT; l++) {
                    if (room == null) {
                        mapStringArray[(y * ROOM_HEIGHT) + l] += new String(new char[ROOM_WIDTH]).replace('\0', ' ');
                    }
                    else {
                        mapStringArray[(y * ROOM_HEIGHT) + l] += room.getMapPiece()[l];
                    }
                }
            }
        }

        return String.join("\n", mapStringArray);
    }
}
