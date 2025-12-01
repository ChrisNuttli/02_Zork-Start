package ch.bbw.zork;

import ch.bbw.zork.Items.*;
import ch.bbw.zork.enums.*;
import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.interfaces.Hidden;
import ch.bbw.zork.interfaces.Unlock;

import java.lang.reflect.Array;
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
        this.roomList.clear();
        this.doorList.clear();
        this.furnitureList.clear();
        this.itemList.clear();

        System.out.println("Generating map...");
        if (Zork2.DEBUG) System.out.println("Building the House...");
        for (int i = 0; i < maxIterations; i++) {
            try {
                generateRooms();

                if (Zork2.DEBUG) {
                    System.out.println("Room Count: " + roomList.size());
                }
                break;
            }
            catch(ConcurrentModificationException e) {
                throw e;
            }
            catch (Exception e) {
                if (i == maxIterations-1) {
                    System.err.println(e.getMessage());
                    throw e;
                }
            }
        }

        generateFurniture();
        generateDoors();
        calculateDepthScores();

        Item frontDoorKey = null;

        for (Room room : roomList) {
            if (room.getRoomData() == RoomData.FRONT_YARD) {
                for (Direction dir : room.getDoorFrames()) {
                    room.lockDoor(dir);
                }
                frontDoorKey = itemList.iterator().next();
                room.placeItem(frontDoorKey);
            }
        }
        generateDoorLocks();
        generateItems();
        generateSafe();
        placeItems(frontDoorKey);
//        hideItems(frontDoorKey);

        if (Zork2.DEBUG) System.out.println("Done!");
    }

    private void hideItems(Item frontDoorKey) {
        HashSet<Unlock> unlockItems = new HashSet<>();
        for (Item item : itemList) {
            if (item == frontDoorKey) {
                continue;
            }
            if (item instanceof Unlock) {
                unlockItems.add((Unlock) item);
            }
        }

        for (Unlock unlock : unlockItems) {
            hideInRandomRoom((Hidden) unlock);
        }
    }

    private void hideInRandomRoom(Hidden item) {
        int randomInt;
        ArrayList<Room> rooms = new ArrayList<>(roomList);
        ArrayList<Room> roomCandidates = new ArrayList<>(rooms);

        while (!roomCandidates.isEmpty()) {
            int i = 0;
            randomInt = Game.getRandom().nextInt(roomList.size());
            roomCandidates = new ArrayList<>(rooms);

            for (Room room : roomCandidates) {
                if (randomInt == i) {
                    try {
                        room.placeItem((Item)item);
                        return;
                    }
                    catch(Exception ignored) {
                        break;
                    }
                }
                i++;
            }
        }

        throw new RuntimeException("Could not hide the Item in any Room");
    }

    private void placeItems(Item frontDoorKey) {
        ArrayList<Item> failItems = new ArrayList<>();
        int success = 0;
        for (Item item : itemList) {
            if (item == frontDoorKey) {
                continue;
            }

            int itemDepth = 9999;
            if (item instanceof Unlock) {
                itemDepth = ((Unlock) item).getMaxDepth();
            }

            Room[] rooms = (Room[])getShuffledList(roomList);
            int successInit = success;

            for (Room room : rooms) {
                if (room.getDepthScore() > itemDepth || room.getRoomData() == RoomData.FRONT_YARD) {
                    continue;
                }

                try {
                    room.placeItem(item);
                    success++;
                    break;
                }
                catch(Exception ignored) {
                    failItems.add(item);
                }
            }

            if (successInit == success) {
                failItems.add(item);
            }
        }

        for (Item item : failItems) {
            this.itemList.remove(item);
        }
    }

    private <T> T[] getShuffledList(HashSet<T> orderedList) {
        HashSet<T> unusedList =  new HashSet<>(orderedList);
        HashSet<T> list =  new HashSet<>(orderedList);
        T ele = list.iterator().next();
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(ele.getClass(), orderedList.size());
        int done = 0;

        while (!unusedList.isEmpty())  {
            list.clear();
            list.addAll(unusedList);
            int randomInt = 0;
            if (!list.isEmpty()) {
                randomInt = Game.getRandom().nextInt(list.size());
            }
            int i = 0;

            for (T element : list) {
                if (randomInt == i) {
                    result[done] = element;
                    done++;
                    unusedList.remove(element);
                    break;
                }
                i++;
            }
        }

        return (T[])result;
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

        // Validate the generated roomlist
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
                HashSet<Direction> frames = new HashSet<>(room.getDoorFrames());

                for (Direction dir : frames) {
                    Room neighbor = getRoom(getNeighborCoordinates(room.getX(), room.getY(), dir));
                    if (neighbor == null || !neighbor.getDoorFrames().contains(dir.getOpposite())) {
                        room.removeDoorFrame(dir);
                    }
                }
            }
        }
    }

    private void calculateDepthScores() {
        // calculate the depth scores of all rooms
        boolean done = false;
        while (!done) {
            done = true;
            for (Room room : roomList) {
                if (!room.calculateDepthScore()) {
                    done = false;
                }
            }
        }
    }

    private void generateDoors() {
        if (Zork2.DEBUG) System.out.println("Installing rudimentary privacy equipment...");
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
        if (Zork2.DEBUG) System.out.println("Adding functional features...");
        for (Room room : roomList) {
            ArrayList<FurnitureData> furnitureDataList = room.getRoomData().getFurnitureData();
            for (FurnitureData furnitureData : furnitureDataList) {
                if (furnitureData == FurnitureData.SAFE) {continue;}
                Furniture furniture = new Furniture(furnitureData, room.getName());
                room.addFurniture(furniture);
            }
        }
    }

    private void generateItems() {
        if (Zork2.DEBUG) System.out.println("Creating usable elements...");

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
        if (Zork2.DEBUG) System.out.println("Reinforcing Security...");

        int success = 0;
        for (int i = 0; i < LOCKED_DOORS_COUNT; i++) {
            Room[] shuffledRoomList = getShuffledList(roomList);

            for (Room room : shuffledRoomList) {
                if (room.getRoomData() == RoomData.FRONT_YARD) {
                    continue;
                }

                int successInit = success;
                Door[] shuffledDoors = getShuffledList(room.getDoors());
                for (Door door : shuffledDoors) {
                    try {
                        door.lockDoor();
                        success++;
                        break;
                    }
                    catch (Exception ignored) {
                        Lock lock = door.getLock();
                        if (lock != null) {
                            this.removeItem(lock.getKey());
                            door.setLock(null);
                        }
                    }
                }

                if (success != successInit) {
                    break;
                }
            }

            if (success == i) {
                throw new RuntimeException("Unable to lock doors!");
            }
        }
    }

    private void generateSafe() {
        if (Zork2.DEBUG) System.out.println("Affix value storage...");
        ArrayList<Room> roomCandidates = new ArrayList<>(roomList);
        ArrayList<Room> rooms = new ArrayList<>(roomCandidates);
        boolean done = false;
        while (!rooms.isEmpty() && !done) {
            rooms = new ArrayList<>(roomCandidates);
            int i = 0;
            int randomIndex = 0;
            if (rooms.size() > 1) {
                randomIndex = Game.getRandom().nextInt(rooms.size());
            }

            for (Room room : rooms) {
                if (room.getDepthScore() < Math.floorDiv(getMaxDepth(), 2)) {
                    roomCandidates.remove(room);
                    continue;
                }
                if (i == randomIndex) {
                    try {
                        room.hideSafe();
                        done = true;
                        break;
                    }
                    catch (Exception e) {
                        roomCandidates.remove(room);
                        break;
                    }
                }
                i++;
            }
        }

        if (!done) {
            HashSet<Furniture> removal = new HashSet<>();
            for (Furniture furniture : furnitureList) {
                if (furniture.getFurnitureData() == FurnitureData.SAFE) {
                    removal.add(furniture);
                }
            }

            for (Furniture furniture : removal) {
                this.furnitureList.remove(furniture);
            }
            throw new IllegalStateException("Safe was not able to be generated!");
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

    public void removeItem(Item item) {
        this.itemList.remove(item);
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
