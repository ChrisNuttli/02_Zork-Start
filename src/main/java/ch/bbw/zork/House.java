package ch.bbw.zork;

import ch.bbw.zork.interfaces.ExitSouth;
import ch.bbw.zork.rooms.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static ch.bbw.zork.Constants.*;

public class House {
    private static House instance = null;
    private ArrayList<Class<? extends Room>> unusedRoomTypes;
    private MapTile[] mapTiles;

    private House() {
        unusedRoomTypes = new ArrayList<>();
        unusedRoomTypes.add(Attic.class);
        unusedRoomTypes.add(Basement.class);
        unusedRoomTypes.add(Bathroom.class);
        unusedRoomTypes.add(Bedroom.class);
        unusedRoomTypes.add(Cellar.class);
        unusedRoomTypes.add(DiningRoom.class);
        unusedRoomTypes.add(FrontYard.class);
        unusedRoomTypes.add(Kitchen.class);
        unusedRoomTypes.add(Livingroom.class);
        unusedRoomTypes.add(Office.class);

        mapTiles = new MapTile[MAP_WIDTH * MAP_HEIGHT];
        int i = 0;
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++, i++) {
                mapTiles[i] = new MapTile(x, y);
            }
        }

        String[][] entropyGrid = new  String[MAP_WIDTH][MAP_HEIGHT];
        for (MapTile tile : mapTiles) {
            tile.collapse();
            entropyGrid[tile.x][tile.y] = String.format("%s", tile.getEntropy());
        }

        for (String[] line : entropyGrid) {
            System.out.println(Arrays.deepToString(line));
        }
    }

    public static synchronized House getInstance() {
        if (instance == null) {
            instance = new House();
        }

        return instance;
    }

    public void generateHouse() {
        Random rand = new Random();
        int startTile = rand.nextInt(4);
        MapTile tile;
        if (startTile == 0) { // North
            tile = getMapTile(HALF_MAP_WIDTH, 0);
        }
        else if (startTile == 1) {
            tile = getMapTile(MAP_WIDTH, HALF_MAP_HEIGHT);
        }
        else if (startTile == 2) {
            tile = getMapTile(HALF_MAP_WIDTH, MAP_HEIGHT);
        }
        else {
            tile = getMapTile(0, HALF_MAP_HEIGHT);
        }

        tile.setRoom(new FrontYard(tile.x, tile.y));
    }

    private MapTile getMapTile(int x, int y) {
        if (x < 0 || x >= MAP_WIDTH || y < 0 || y >= MAP_HEIGHT) {
            return null;
        }
        int index = (y * MAP_WIDTH) + x;
        return mapTiles[index];
    }

    private class MapTile {
        private ArrayList<Class<? extends Room>> validRoomTypes;
        private ArrayList<Direction[]> validDoorSlots;
        private HashMap<Class<? extends Room>, ArrayList<Direction[]>> validConfigurations;
        private Room room;
        private int x;
        private int y;

        public MapTile(int x, int y) {
            this.x = x;
            this.y = y;
            this.validRoomTypes = new ArrayList<>();
            validRoomTypes.add(Attic.class);
            validRoomTypes.add(Basement.class);
            validRoomTypes.add(Bathroom.class);
            validRoomTypes.add(Bedroom.class);
            validRoomTypes.add(Cellar.class);
            validRoomTypes.add(DiningRoom.class);
            validRoomTypes.add(Kitchen.class);
            validRoomTypes.add(Livingroom.class);
            validRoomTypes.add(Office.class);
            if ((x == 0 || x == HALF_MAP_WIDTH || x == MAP_WIDTH-1)
                && (y == 0 || y == HALF_MAP_HEIGHT || y == MAP_HEIGHT-1)) {
                validRoomTypes.add(FrontYard.class);
            }

            validConfigurations = new HashMap<>();
            for (Class<? extends Room> roomType : validRoomTypes) {
                validConfigurations.put(roomType, getDoorSlotList(roomType));
            }

            validConfigurations.put(Corridor.class, getDoorSlotList(Corridor.class));
        }

        private ArrayList<Direction[]> getDoorSlotList(Class<? extends Room> roomType) {
            ArrayList<Direction[]> doorSlots = new ArrayList<>();
            try {
                Constructor constr = roomType.getConstructors()[0];
                Room room = (Room) constr.newInstance();
                RoomShape shape = (RoomShape) roomType.getMethod("getShape").invoke(room);

                switch(shape) {
                    case DEAD_END:
                        doorSlots.add(new Direction[]{Direction.NORTH});
                        doorSlots.add(new Direction[]{Direction.EAST});
                        doorSlots.add(new Direction[]{Direction.SOUTH});
                        doorSlots.add(new Direction[]{Direction.WEST});
                        break;
                    case BEND:
                        doorSlots.add(new Direction[]{Direction.NORTH, Direction.EAST});
                        doorSlots.add(new Direction[]{Direction.EAST, Direction.SOUTH});
                        doorSlots.add(new Direction[]{Direction.SOUTH, Direction.WEST});
                        doorSlots.add(new Direction[]{Direction.WEST, Direction.NORTH});
                        break;
                    case STRAIGHT:
                        doorSlots.add(new Direction[]{Direction.NORTH, Direction.SOUTH});
                        doorSlots.add(new Direction[]{Direction.EAST, Direction.WEST});
                        break;
                    case FORK:
                        doorSlots.add(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH});
                        doorSlots.add(new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST});
                        doorSlots.add(new Direction[]{Direction.NORTH, Direction.WEST, Direction.SOUTH});
                        doorSlots.add(new Direction[]{Direction.NORTH, Direction.EAST, Direction.WEST});
                        break;
                    case CROSS:
                        doorSlots.add(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST});
                        break;
                }
            } catch (InvocationTargetException e) {
                System.err.println(e.getTargetException().getMessage());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            return doorSlots;
        }

        public int getEntropy() {
            int count = 0;
            for (Class<? extends Room> roomType : validConfigurations.keySet()) {
                count += validConfigurations.get(roomType).size();
            }

            return count;
        }

        public ArrayList<Class<? extends Room>> getValidRoomTypes() {
            return validRoomTypes;
        }

        public void addValidRoomType(Class<? extends Room> roomType) {
            validRoomTypes.add(roomType);
        }

        public void collapse() {
            if ((x != 0 && x != HALF_MAP_WIDTH && x != MAP_WIDTH-1)
                || (y != 0 && y != HALF_MAP_HEIGHT && y != MAP_HEIGHT)) {
                validConfigurations.remove(FrontYard.class);
            }

            ArrayList<Class<? extends Room>> removedRoomTypes = new ArrayList<>();

            for (Class<? extends Room> roomType : validConfigurations.keySet()) {
                if (!validRoomTypes.contains(roomType)) {
                    removedRoomTypes.add(roomType);
                    continue;
                }

                ArrayList<Direction[]> doorSlotList = validConfigurations.get(roomType);
                ArrayList<Direction[]> removeList = new ArrayList<>();
                for (Direction[] doorSlots : doorSlotList) {
                    // Check borders
                    if ((y == 0 && Arrays.asList(doorSlots).contains(Direction.NORTH))
                    || (x == MAP_WIDTH-1 && Arrays.asList(doorSlots).contains(Direction.EAST))
                    || (y == MAP_HEIGHT-1 && Arrays.asList(doorSlots).contains(Direction.SOUTH))
                    || (x == 0 && Arrays.asList(doorSlots).contains(Direction.WEST))) {
                        removeList.add(doorSlots);
                    }

                    removeList.addAll(connectDoorSlotsWithNeighbor(Direction.NORTH, doorSlots));
                    removeList.addAll(connectDoorSlotsWithNeighbor(Direction.EAST, doorSlots));
                    removeList.addAll(connectDoorSlotsWithNeighbor(Direction.SOUTH, doorSlots));
                    removeList.addAll(connectDoorSlotsWithNeighbor(Direction.WEST, doorSlots));
                }

                for (Direction[] slots : removeList) {
                    doorSlotList.remove(slots);
                }
            }

            for (Class<? extends Room> roomType : removedRoomTypes) {
                validConfigurations.remove(roomType);
            }
        }

        private ArrayList<Direction[]> connectDoorSlotsWithNeighbor(Direction dir, Direction[] doorSlots) {
            ArrayList<Direction[]> removeList = new ArrayList<>();
            MapTile tile = getMapTile(x, y-1);
            if (tile != null) {
                Room northRoom = tile.getRoom();
                if (northRoom != null) {
                    ExitSouth southExit = northRoom.getExitSouth();
                    if ((southExit != null && !Arrays.asList(doorSlots).contains(dir))
                            || (southExit == null && Arrays.asList(doorSlots).contains(dir))) {
                        removeList.add(doorSlots);
                    }
                }
            }

            return removeList;
        }

        public void setRoom(Room room) {

        }

        public Room getRoom() {
            return room;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
