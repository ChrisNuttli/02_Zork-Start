package ch.bbw.zork;

import ch.bbw.zork.Items.*;
import ch.bbw.zork.enums.*;
import ch.bbw.zork.interfaces.Unlock;

import java.util.*;

import static ch.bbw.zork.Constants.*;

public class House {
    private HashSet<Room> roomList;
    private HashSet<Door> doorList;
    private HashSet<Furniture> furnitureList;
    private HashSet<Item> itemList;
    private final int maxIterations = Integer.MAX_VALUE;

    public House() {
        roomList = new HashSet<>();
        doorList = new HashSet<>();
        furnitureList = new HashSet<>();
        itemList = new HashSet<>();
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

        generateDoors();
        generateFurniture();

        // TODO: Generate Items
        generateItems();
        placeItems();

        // TODO: Generate Safe

        System.out.println("Done!");
    }

    private void placeItems() {
        for (Item item : itemList) {
            if (item instanceof Unlock) {
                int depth = doorList.stream().filter(door ->
                        door.getLock() != null &&
                        door.getLock().getKey() == item
                ).findFirst().map(door ->
                        Game.getRandom().nextInt(door.getDepthScore() - 1)).orElse(0);

                HashSet<Room> hideRoomCandidates = new HashSet<>(roomList);
                hideRoomCandidates.removeIf((room) -> room.getDepthScore() > depth);

                int randomIndex = Game.getRandom().nextInt(hideRoomCandidates.size());
                int i = 0;
                for (Room room : hideRoomCandidates) {
                    if (i == randomIndex) {
                        room.hideItem(item);
                        break;
                    }
                    i++;
                }
            }
            else {
                HashSet<Room> hideRoomCandidates = new HashSet<>(roomList);
                hideRoomCandidates.removeIf((room) -> room.getDepthScore() == 0);

                int randomIndex = Game.getRandom().nextInt(hideRoomCandidates.size());
                int i = 0;
                for (Room room : hideRoomCandidates) {
                    if (i == randomIndex) {
                        room.hideItem();
                        break;
                    }
                    i++;
                }
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

        // calculate the depth scores of all rooms
        boolean done = false;
        while (!done) {
            done = true;
            for (Room room : roomList) {
                int initialScore = room.getDepthScore();
                room.calculateDepthScore();
                if (initialScore != room.getDepthScore()) {
                    done = false;
                }
            }
        }
    }

    private void generateDoors() {
        for (Room room : roomList) {
            for (Direction dir : room.getDoorFrames()) {
                if (room.getDoor(dir) != null) {
                    continue;
                }

                Room neighbor = getRoom(getNeighborCoordinates(room.getX(), room.getY(), dir));
                Door door = new Door(dir.getOpposite(), room, dir, neighbor);
                doorList.add(door);
            }
        }
    }

    private void generateFurniture() {
        for (Room room : roomList) {
            ArrayList<FurnitureData> furnitureDataList = room.getRoomData().getFurnitureData();
            for (FurnitureData furnitureData : furnitureDataList) {
                if (furnitureData == FurnitureData.SAFE) {continue;}
                Furniture furniture = new Furniture(furnitureData);
                room.addFurniture(furniture);
            }
        }
    }

    private void generateItems() {
        generateDoorLocks();

        // Generate the rest of the items
        for (ItemData id : ITEM_DATA_LIST) {
            if (id.getSpawnProbability() < Game.getRandom().nextDouble()) {
                continue;
            }

            int spawnCount = Game.getRandom().nextInt(id.getMaxSpawns() -  id.getMinSpawns()) +  id.getMinSpawns();
            for (int i = 0; i < spawnCount; i++) {
                switch(id) {
                    case BACKPACK:
                        new Backpack();
                        break;
                    case TIME_NOTE:
                        new TimeNote();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void generateDoorLocks() {
        int randomIndexA = Game.getRandom().nextInt(doorList.size());
        int randomIndexB = Game.getRandom().nextInt(doorList.size());
        int randomIndexC = Game.getRandom().nextInt(doorList.size());

        while (randomIndexA == randomIndexB || randomIndexC == randomIndexA || randomIndexC == randomIndexB) {
            randomIndexA = Game.getRandom().nextInt(doorList.size());
            randomIndexB = Game.getRandom().nextInt(doorList.size());
            randomIndexC = Game.getRandom().nextInt(doorList.size());
        }

        int i = 0;
        for (Door door : doorList) {
            if (i == randomIndexA || i == randomIndexB || i == randomIndexC) {
                Lock lock = new Lock(LockType.KEY_HOLE);
                door.setLock(lock);
            }

            i++;
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

    public void addFurniture(Furniture furniture) {
        this.furnitureList.add(furniture);
    }

    public void addItem(Item item) {
        this.itemList.add(item);
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

    public int countRoom(RoomData roomData) {
        int count = 0;
        for (Room room : roomList) {
            if (room.getRoomData() == roomData) {
                count++;
            }
        }

        return count;
    }

    private int getMaxDepth() {
        int maxDepth = 0;
        for (Room room : roomList) {
            maxDepth = Math.max(maxDepth, room.getDepthScore());
        }

        return maxDepth;
    }
}
