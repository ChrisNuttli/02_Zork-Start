package ch.bbw.zork;

import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.enums.RoomData;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.NorthPassage;
import ch.bbw.zork.interfaces.SouthPassage;
import ch.bbw.zork.interfaces.WestPassage;

import java.util.*;

import static ch.bbw.zork.Constants.*;

public class House {
    public ArrayList<Room> roomList;
    public ArrayList<Furniture> furnitureList;
    private Room playerLocation;
    private Game game;

    public House(Game game) {
        this.game = game;
        roomList = new ArrayList<>();
    }

    public void generateHouse() {
        System.out.println("Please wait. Generating a new map...");

        for (int i = 0; i < 10000; i++) {
            try {
                generateRooms();
                if (roomList.size() < ROOM_DATA_LIST.length) {
                    throw new RuntimeException("Not all room types were used in the generation");
                }

                for (Room room : roomList) {
                    if (room.getRoomData() == RoomData.CORRIDOR) {
                        Room northRoom = getRoom(getNeighborCoordinates(room.getX(), room.getY(), Direction.NORTH));
                        Room eastRoom = getRoom(getNeighborCoordinates(room.getX(), room.getY(), Direction.EAST));
                        Room southRoom = getRoom(getNeighborCoordinates(room.getX(), room.getY(), Direction.SOUTH));
                        Room westRoom = getRoom(getNeighborCoordinates(room.getX(), room.getY(), Direction.WEST));
                        if (northRoom == null || !northRoom.getDoorFrames().contains(Direction.SOUTH)) {
                            room.removeDoorFrame(Direction.NORTH);
                        }

                        if (eastRoom == null || !eastRoom.getDoorFrames().contains(Direction.WEST)) {
                            room.removeDoorFrame(Direction.EAST);
                        }

                        if (southRoom == null || !southRoom.getDoorFrames().contains(Direction.NORTH)) {
                            room.removeDoorFrame(Direction.SOUTH);
                        }

                        if (westRoom == null || !westRoom.getDoorFrames().contains(Direction.EAST)) {
                            room.removeDoorFrame(Direction.WEST);
                        }
                    }
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
        Room frontYard = new Room((int)Math.ceil((double)MAP_WIDTH / 2), MAP_HEIGHT-1, RoomData.FRONT_YARD, this, this.game);
        roomList.add(frontYard);

        ArrayList<Room> rooms;
        boolean done = false;

        while(!done) {
            if (roomList.size() > ROOM_DATA_LIST.length * 2) {
                throw new RuntimeException("Too many rooms in this house");
            }

            rooms = new ArrayList<>(roomList);
            for (Room room : rooms) {
                ArrayList<Direction> missing = room.getMissingNeighborDirections();
                if (missing.isEmpty()) {
                    continue;
                }
                for (Direction direction : missing) {
                    RoomData neighborData = room.decideNeighbor(direction);
                    if (neighborData != null) {
                        int[] coordinates = getNeighborCoordinates(room.getX(), room.getY(), direction);
                        roomList.add(new Room(coordinates[0], coordinates[1], neighborData, this, this.game));
                    }
                    else {
                        throw new RuntimeException("No suitable candidate was found");
                    }
                }
            }

            done = roomList.size() == rooms.size();
        }
    }

    private void generateDoors() {
        for (Room room : roomList) {
            try {
                for (Direction dir : room.getDoorFrames()) {
                    if (room.getDoor(dir) != null) {
                        continue;
                    }

                    if ((dir == Direction.NORTH && room.getY() == 0) ||
                        (dir == Direction.SOUTH && room.getY() == MAP_HEIGHT - 1) ||
                        (dir == Direction.EAST && room.getX() == MAP_WIDTH - 1) ||
                        (dir == Direction.WEST && room.getX() == 0)) {
                        throw new RuntimeException("Cannot create room outside of map");
                    }

                    Room neighbor = getRoom(getNeighborCoordinates(room.getX(), room.getY(), dir));
                    Door door = new Door(dir, neighbor, dir.getOpposite(), room);
                    room.addDoor(dir, door);
                    neighbor.addDoor(dir.getOpposite(), door);
                }
            }
            catch(Exception e) {
//                System.err.println("Error while adding a door to " + room.getName());
//                for (Direction dir : room.getDoorFrames()) {
//                    System.err.print(dir + ": " + getRoom(getNeighborCoordinates(room.getX(), room.getY(), dir)));
//                }
                if (e instanceof RuntimeException) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }

    public ArrayList<RoomData> getCandidatesForCoodinates(int[] coordinates) {
        return getCandidatesForCoodinates(coordinates[0], coordinates[1]);
    }

    public ArrayList<RoomData> getCandidatesForCoodinates(int x, int y) {
        if (getRoom(x,y) != null) return null;

        ArrayList<RoomData> candidates = new ArrayList<>(Arrays.asList(ROOM_DATA_LIST));
        candidates.add(RoomData.CORRIDOR);

        Room northRoom = getRoom(x, y-1);
        Room eastRoom = getRoom(x+1, y);
        Room southRoom = getRoom(x, y+1);
        Room westRoom = getRoom(x-1, y);

        if (northRoom != null) {
            if (!northRoom.getDoorFrames().contains(Direction.SOUTH)) {
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (Arrays.asList(rd.getDoorFrames()).contains(Direction.NORTH)) {
                        candidates.remove(rd);
                    }
                }
            }
            else {
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (!Arrays.asList(rd.getDoorFrames()).contains(Direction.NORTH)) {
                        candidates.remove(rd);
                    }
                }
            }
        }

        if (eastRoom != null) {
            if (!eastRoom.getDoorFrames().contains(Direction.WEST)) {
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (Arrays.asList(rd.getDoorFrames()).contains(Direction.EAST)) {
                        candidates.remove(rd);
                    }
                }
            }
            else {
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (!Arrays.asList(rd.getDoorFrames()).contains(Direction.EAST)) {
                        candidates.remove(rd);
                    }
                }
            }
        }

        if (southRoom != null) {
            if (!southRoom.getDoorFrames().contains(Direction.NORTH)) {
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (Arrays.asList(rd.getDoorFrames()).contains(Direction.SOUTH)) {
                        candidates.remove(rd);
                    }
                }
            }
            else {
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (!Arrays.asList(rd.getDoorFrames()).contains(Direction.SOUTH)) {
                        candidates.remove(rd);
                    }
                }
            }
        }

        if (westRoom != null) {
            if (!westRoom.getDoorFrames().contains(Direction.EAST)) {
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (Arrays.asList(rd.getDoorFrames()).contains(Direction.WEST)) {
                        candidates.remove(rd);
                    }
                }
            }
            else {
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (!Arrays.asList(rd.getDoorFrames()).contains(Direction.WEST)) {
                        candidates.remove(rd);
                    }
                }
            }
        }

        return candidates;
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

    public int[] getNeighborCoordinates(int x, int y, Direction direction) {
        return getNeighborCoordinates(new int[]{x, y}, direction);
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

    public Room findRoomByCoordinates(int[] coordinates) {
        return findRoomByCoordinates(coordinates[0], coordinates[1]);
    }

    public Room findRoomByCoordinates(int x, int y) {
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

    public ArrayList<RoomData> getUsedRoomTypes() {
        ArrayList<RoomData> roomTypeList = new ArrayList<>();
        for (Room room : roomList) {
            roomTypeList.add(room.getRoomData());
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

        ArrayList<String> cleanMap = new ArrayList<>();

        for (String line : mapStringArray) {
            if (!line.trim().isEmpty()) {
                cleanMap.add(line);
            }
        }

        int leftmost = Integer.MAX_VALUE;
        for (String line : cleanMap) {
            leftmost = Math.min(leftmost, line.indexOf(WALL_CHAR));
        }

        for (int i = 0; i < cleanMap.size(); i++) {
            String line = cleanMap.get(i);
             cleanMap.set(i, line.substring(leftmost));
        }

        mapStringArray = cleanMap.toArray(new String[0]);

        return String.join("\n", mapStringArray);
    }
}
