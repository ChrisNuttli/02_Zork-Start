package ch.bbw.zork;

import ch.bbw.zork.Items.*;
import ch.bbw.zork.Items.Crowbar;
import ch.bbw.zork.Items.Item;
import ch.bbw.zork.enums.*;

import java.util.*;

import static ch.bbw.zork.Constants.*;

public class House {
    private HashSet<Room> roomList;
    private final int maxIterations = 10000;

    public House() {
        roomList = new HashSet<>();
    }

    public void generateHouse() {
        System.out.println("Please wait. Generating a new map...");

        for (int i = 0; i < maxIterations; i++) {
            try {
                generateRooms();

                if (Zork2.DEBUG) {
                    System.out.println("Room Count: " + roomList.size());
                }
                break;
            }
            catch (Exception e) {
                if (i == maxIterations-1) {
                    System.err.println(e.getMessage());
                    throw e;
                }
            }
        }

        // TODO: Generate Doors
        // TODO: Generate Furniture
        // TODO: Generate Items
    }

    private void generateSafe() {
        int maxDepthScore = 0;
        for (Room room : roomList) {
            maxDepthScore = Math.max(maxDepthScore, room.getDepthScore());
        }

        ArrayList<Room> safeRooms = new ArrayList<>(roomList);
        int roomIndex = 0;

        while (!safeRooms.isEmpty()) {
            try {
                roomIndex = new Random().nextInt(safeRooms.size());
                if (safeRooms.get(roomIndex).getDepthScore() < Math.floorDiv(maxDepthScore, 2)) {
                    throw new RuntimeException("Room is not deep enough");
                }

                Room safeRoom = safeRooms.get(roomIndex);
                ArrayList<Furniture> furnitureList = safeRoom.getFurnitureList();
                int furnitureIndex = 0;
                FurnitureData fd = null;
                while (fd.getHidingSpot().isEmpty() && !furnitureList.isEmpty()) {
                    if (fd != null) {
                        furnitureList.remove(furnitureList.get(furnitureIndex));
                    }
                    furnitureIndex = new Random().nextInt(furnitureList.size());
                    fd = furnitureList.get(furnitureIndex).getFurnitureData();
                }

                if (furnitureList.isEmpty()) {
                    throw new RuntimeException("No valid furniture found in room!");
                }

                Furniture safe = new Furniture(FurnitureData.SAFE);
                Lock keyLock = new Lock(LockType.KEY_HOLE);
                Lock codeLock = new Lock(LockType.NUMPAD);

                LocationNote locationNote = safe.generatLocationNote(fd);
                Key safeKey = keyLock.getKey();
                String safeCode = codeLock.getCode();
                Note safeCodeNote = new Note("Note", String.format("safe: %s", safeCode));

                hideItem(safeKey);
                hideItem(safeCodeNote);
                hideItem(locationNote);
            }
            catch(Exception e) {
                safeRooms.remove(roomIndex);
            }
        }
    }

    private void generateRooms() {
        roomList.clear();
        new Room((int)Math.ceil((double)MAP_WIDTH / 2), MAP_HEIGHT-1, RoomData.FRONT_YARD);
        HashSet<Room> rooms = new HashSet<>();

        do {
            rooms.addAll(roomList);
            for (Room room : rooms) {
                room.generateNeighbors();
            }
        } while (rooms.size() != roomList.size());

        HashMap<RoomData, Integer> roomCount = new HashMap<>();

        for (Room room : roomList) {
            RoomData rd = room.getRoomData();
            if (!roomCount.containsKey(rd)) {
                roomCount.put(rd, 0);
            }

            roomCount.put(rd, roomCount.get(rd) + 1);
        }

        if (roomCount.size() < ROOM_DATA_LIST.length) {
            throw new IllegalStateException("Not all Rooms got generated!");
        }

        for (RoomData rd : ROOM_DATA_LIST) {
            if (rd == RoomData.CORRIDOR) {continue;}
            if (!roomCount.containsKey(rd)) {
                throw new IllegalStateException("Not all Rooms got generated!");
            }

            int count = roomCount.get(rd);
            if (count != 1) {
                throw new IllegalStateException("Some Rooms were generated multiple times!");
            }
        }

        // Remove invalid doorframes from corridors
        for (Room room : roomList) {
            if (room.getRoomData() == RoomData.CORRIDOR) {
                for (Direction dir : room.getDoorFrames()) {
                    Room neighbor = getRoom(getNeighborCoordinates(room.getX(), room.getY(), dir));
                    if (neighbor == null || !neighbor.getDoorFrames().contains(dir.getOpposite())) {
                        room.removeDoorFrame(dir);
                    }
                }
            }
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

    private void generateFurniture() {
        for (Room room : roomList) {
            RoomData roomData = room.getRoomData();
            ArrayList<FurnitureData> furnitureDataList = getFurnitureDataForRoom(roomData);
            int min = furnitureDataList.size() / 2;
            int furnitureCount = Game.getRandom().nextInt((furnitureDataList.size()+1) - min) + min;
            while (room.getFurnitureList().size() < furnitureCount) {
                int randomIndex = Game.getRandom().nextInt(furnitureDataList.size());
                FurnitureData furnitureData = furnitureDataList.get(randomIndex);
                if (room.getFurnitureCount(furnitureData) < furnitureData.getMax(roomData)) {
                    room.addFurniture(furnitureData);
                }
            }
        }
    }

    private void generateItems() {
        ArrayList<Item> itemList = new ArrayList<>();
        for (ItemData itemData : ITEM_DATA_LIST) {
            if (itemData == ItemData.KEY || itemData == ItemData.LOCATION_NOTE) {continue;}
            if (itemData.getSpawnProbability() >= Game.getRandom().nextInt(100)) {
                int min = Math.max(1, itemData.getMinSpawns());
                int spawnCount = Game.getRandom().nextInt(itemData.getMaxSpawns()+1 - min) + min;
                for (int i = 0; i < spawnCount; i++) {
                    switch (itemData) {
                        case BACKPACK:
                            itemList.add(new Backpack());
                            break;
                        case FLASHLIGHT:
                            itemList.add(new Flashlight());
                            break;
                        case CROWBAR:
                            itemList.add(new Crowbar());
                            break;
                        case TIME_NOTE:
                            itemList.add(new TimeNote());
                    }
                }
            }
        }
    }

    private ArrayList<FurnitureData> getFurnitureDataForRoom(RoomData roomData) {
        ArrayList<FurnitureData> furnitureData = new ArrayList<>();
        for (FurnitureData fd : FURNITURE_DATA_LIST) {
            if (fd.getMax(roomData) > 0) {
                furnitureData.add(fd);
            }
        }

        return furnitureData;
    }

    public ArrayList<RoomData> getCandidatesForCoordinates(int[] coordinates) {
        return getCandidatesForCoordinates(coordinates[0], coordinates[1]);
    }

    public ArrayList<RoomData> getCandidatesForCoordinates(int x, int y) {
        if (getRoom(x,y) != null) return null;

        ArrayList<RoomData> candidates = new ArrayList<>(Arrays.asList(ROOM_DATA_LIST));

        Room northRoom = getRoom(x, y-1);
        Room eastRoom = getRoom(x+1, y);
        Room southRoom = getRoom(x, y+1);
        Room westRoom = getRoom(x-1, y);

        if (northRoom != null) {
            if (!northRoom.getDoorFrames().contains(Direction.SOUTH)) {
                // No door towards north is possible
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (Arrays.asList(rd.getDoorFrames()).contains(Direction.NORTH)) {
                        candidates.remove(rd);
                    }
                }
            }
            else {
                // A door towards north must be included
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (!Arrays.asList(rd.getDoorFrames()).contains(Direction.NORTH) ||
                            (northRoom.getRoomData().getShape() == RoomShape.DEAD_END && rd.getShape() == RoomShape.DEAD_END)
                    ) {
                        candidates.remove(rd);
                    }
                }
            }
        }
        else if (y == 0) {
            // No door to the north is possible
            for (RoomData rd : ROOM_DATA_LIST) {
                if (!candidates.contains(rd)) {continue;}
                if (Arrays.asList(rd.getDoorFrames()).contains(Direction.NORTH)) {
                    candidates.remove(rd);
                }
            }
        }

        if (eastRoom != null) {
            if (!eastRoom.getDoorFrames().contains(Direction.WEST)) {
                // No door towards east is possible
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (Arrays.asList(rd.getDoorFrames()).contains(Direction.EAST)) {
                        candidates.remove(rd);
                    }
                }
            }
            else {
                // A door towards east must be included
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (!Arrays.asList(rd.getDoorFrames()).contains(Direction.EAST) ||
                            (eastRoom.getRoomData().getShape() == RoomShape.DEAD_END && rd.getShape() == RoomShape.DEAD_END)
                    ) {
                        candidates.remove(rd);
                    }
                }
            }
        }
        else if (x == MAP_WIDTH - 1) {
            // No door towards east is possible
            for (RoomData rd : ROOM_DATA_LIST) {
                if (!candidates.contains(rd)) {continue;}
                if (Arrays.asList(rd.getDoorFrames()).contains(Direction.EAST)) {
                    candidates.remove(rd);
                }
            }
        }

        if (southRoom != null) {
            if (!southRoom.getDoorFrames().contains(Direction.NORTH)) {
                // No door towards south is possible
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (Arrays.asList(rd.getDoorFrames()).contains(Direction.SOUTH)) {
                        candidates.remove(rd);
                    }
                }
            }
            else {
                // A door towards south must be included
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (!Arrays.asList(rd.getDoorFrames()).contains(Direction.SOUTH) ||
                            (southRoom.getRoomData().getShape() == RoomShape.DEAD_END && rd.getShape() == RoomShape.DEAD_END)
                    ) {
                        candidates.remove(rd);
                    }
                }
            }
        }
        else if (y == MAP_HEIGHT - 1) {
            // No door towards south is possible
            for (RoomData rd : ROOM_DATA_LIST) {
                if (!candidates.contains(rd)) {continue;}
                if (Arrays.asList(rd.getDoorFrames()).contains(Direction.SOUTH)) {
                    candidates.remove(rd);
                }
            }
        }

        if (westRoom != null) {
            if (!westRoom.getDoorFrames().contains(Direction.EAST)) {
                // No door towards west is possible
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (Arrays.asList(rd.getDoorFrames()).contains(Direction.WEST)) {
                        candidates.remove(rd);
                    }
                }
            }
            else {
                // A door towards west must be included
                for (RoomData rd : ROOM_DATA_LIST) {
                    if (!candidates.contains(rd)) {continue;}
                    if (!Arrays.asList(rd.getDoorFrames()).contains(Direction.WEST) ||
                            (westRoom.getRoomData().getShape() == RoomShape.DEAD_END && rd.getShape() == RoomShape.DEAD_END)
                    ) {
                        candidates.remove(rd);
                    }
                }
            }
        }
        else if (x == 0) {
            // No door towards west is possible
            for (RoomData rd : ROOM_DATA_LIST) {
                if (!candidates.contains(rd)) {continue;}
                if (Arrays.asList(rd.getDoorFrames()).contains(Direction.WEST)) {
                    candidates.remove(rd);
                }
            }
        }

        candidates.removeAll(getUsedRoomData());
//        candidates.add(RoomData.CORRIDOR);

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

    public void addRoom(Room room) {
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

    public Room getRoom(RoomData roomData) {
        for (Room room : roomList) {
            if (room.getRoomData().equals(roomData)) {
                return room;
            }
        }

        return null;
    }

    public Room getPlayerLocation() {
        Player player = Game.getPlayer();
        int x =  player.getX();
        int y = player.getY();
        return getRoom(x, y);
    }

    public ArrayList<RoomData> getUsedRoomTypes() {
        ArrayList<RoomData> roomTypeList = new ArrayList<>();
        for (Room room : roomList) {
            roomTypeList.add(room.getRoomData());
        }

        return roomTypeList;
    }

    private void hideItem(Item item) {
        int roomIndex = 0;
        ArrayList<Room> itemHideRooms = new ArrayList<>(roomList);
        boolean success = false;

        while (!itemHideRooms.isEmpty()) {
            try {
                roomIndex = Game.getRandom().nextInt(itemHideRooms.size());
                Room itemRoom = itemHideRooms.get(roomIndex);
                itemRoom.hideItem(item);
                success = true;
                break;
            }
            catch(Exception e) {
                System.err.println(e.getMessage());
            }
        }

        if (!success) {
            throw new  RuntimeException("Cannot hide this item.");
        }
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
                    if (room == null || (!room.isDiscovered() && !Zork2.DEBUG)) {
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
            if (line.indexOf(WALL_CHAR) == -1) {
                continue;
            }
            leftmost = Math.min(leftmost, line.indexOf(WALL_CHAR));
        }

        System.out.println(leftmost);
        for (int i = 0; i < cleanMap.size(); i++) {
            String line = cleanMap.get(i);
             cleanMap.set(i, line.substring(leftmost));
        }

        mapStringArray = cleanMap.toArray(new String[0]);

        return String.join("\n", mapStringArray);
    }

    public HashSet<Room> getRoomList() {
        return roomList;
    }

    public ArrayList<RoomData> getUsedRoomData() {
        HashSet<RoomData> roomDataList = new HashSet<>();
        int corridorCount = 0;
        for (Room room : roomList) {
            RoomData roomData = room.getRoomData();
            roomDataList.add(roomData);
            if (roomData == RoomData.CORRIDOR) {
                corridorCount++;
            }
        }

        if (corridorCount < MAX_CORRIDORS) {
            roomDataList.remove(RoomData.CORRIDOR);
        }

        return new ArrayList<>(roomDataList);
    }

    public ArrayList<RoomData> getMissingRoomData() {
        ArrayList<RoomData> roomDataList = new ArrayList<>(Arrays.asList(ROOM_DATA_LIST));
        roomDataList.removeAll(this.getUsedRoomData());
        return roomDataList;
    }

    public int countRoom(RoomData roomData) {
        int count = 0;
        for (Room room : roomList) {
            if (room.getRoomData() == roomData) {
                count++;
            }
        }

        return count;
    }
}
