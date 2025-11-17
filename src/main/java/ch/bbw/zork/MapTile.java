package ch.bbw.zork;

import ch.bbw.zork.Rooms.*;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.NorthPassage;
import ch.bbw.zork.interfaces.SouthPassage;
import ch.bbw.zork.interfaces.WestPassage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapTile {
    private int x;
    private int y;
    private ArrayList<Class<? extends Room>> entropy;
    private boolean isInstantiated;

    public MapTile(int x, int y) {
        this.isInstantiated = false;
        this.x = x;
        this.y = y;

        entropy = new ArrayList<>();
        entropy.add(Attic.class);
        entropy.add(Bathroom.class);
        entropy.add(Bedroom.class);
        entropy.add(Cellar.class);
        entropy.add(Corridor.class);
        entropy.add(DiningRoom.class);
        entropy.add(FrontYard.class);
        entropy.add(Kitchen.class);
        entropy.add(LivingRoom.class);
        entropy.add(Office.class);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Class<? extends Room>> getEntropy() {
        return entropy;
    }

    public boolean isInstantiated() {
        return isInstantiated;
    }

    public void setInstantiated(boolean instantiated) {
        isInstantiated = instantiated;
    }

    public boolean isValid() {
        if (!isInstantiated) {
            return true;
        }

        Class<? extends Room> roomClass = entropy.get(0);
        List<?> interfaces = Arrays.asList(roomClass.getInterfaces());
        if (interfaces.contains(NorthPassage.class) && House.mapTiles[y-1][x].containsPassageClass(Direction.SOUTH)) {
            return true;
        }

        if (interfaces.contains(EastPassage.class) && House.mapTiles[y][x+1].containsPassageClass(Direction.WEST)) {
            return true;
        }

        if (interfaces.contains(SouthPassage.class) && House.mapTiles[y+1][x].containsPassageClass(Direction.NORTH)) {
            return true;
        }

        if (interfaces.contains(WestPassage.class) && House.mapTiles[y][x-1].containsPassageClass(Direction.EAST)) {
            return true;
        }

        return false;
    }

    public boolean containsPassageClass(Direction direction) {
        for (Class<? extends Room> roomClass : entropy) {
            List<?> interfaces = Arrays.asList(roomClass.getInterfaces());
            switch(direction) {
                case NORTH:
                    if (interfaces.contains(NorthPassage.class)) return true;
                    break;
                case EAST:
                    if (interfaces.contains(EastPassage.class)) return true;
                    break;
                case WEST:
                    if (interfaces.contains(SouthPassage.class)) return true;
                    break;
                case SOUTH:
                    if (interfaces.contains(WestPassage.class)) return true;
                    break;
            }
        }

        return false;
    }

    public void collapse() {
        if (entropy.size() < 2) {
            return;
        }

        for (Room room : House.roomList) {
            if (room.getClass() != Corridor.class) {
                entropy.remove(room.getClass());
            }
        }

        ArrayList<Class<? extends Room>> list = new ArrayList<>(entropy);
        for (Class<? extends Room> roomClass : list) {
            if (roomClass == FrontYard.class && (x != Math.floorDiv(Constants.MAP_WIDTH, 2) || y != Constants.MAP_HEIGHT-1)) {
                entropy.remove(FrontYard.class);
                continue;
            }
            List<?> interfaces = Arrays.asList(roomClass.getInterfaces());

            Room northNeighbor = House.findRoomByCoordinates(x, y-1);
            if (interfaces.contains(NorthPassage.class)) {
                if (northNeighbor == null && y > 0) {
                    MapTile northTile = House.mapTiles[y-1][x];
                    boolean northValid = false;
                    for (Class<? extends Room> neighborType : northTile.getEntropy()) {
                        if (Arrays.asList(neighborType.getInterfaces()).contains(SouthPassage.class)) {
                            northValid = true;
                            break;
                        }
                    }

                    if (!northValid) {
                        entropy.remove(roomClass);
                    }
                }
                else  if (!(northNeighbor instanceof SouthPassage) || y < 1){
                    entropy.remove(roomClass);
                }
            }

            Room eastNeighbor = House.findRoomByCoordinates(x+1, y);
            if (interfaces.contains(EastPassage.class)) {
                if (eastNeighbor == null && x < Constants.MAP_WIDTH - 1) {
                    MapTile eastTile = House.mapTiles[y][x+1];
                    boolean eastValid = false;
                    for (Class<? extends Room> neighborType : eastTile.getEntropy()) {
                        if (Arrays.asList(neighborType.getInterfaces()).contains(WestPassage.class)) {
                            eastValid = true;
                            break;
                        }
                    }

                    if (!eastValid) {
                        entropy.remove(roomClass);
                    }
                }
                else  if (!(northNeighbor instanceof SouthPassage) || x >= Constants.MAP_WIDTH){
                    entropy.remove(roomClass);
                }
            }

            Room southNeighbor = House.findRoomByCoordinates(x, y+1);
            if (interfaces.contains(SouthPassage.class)) {
                if (southNeighbor == null && y < Constants.MAP_HEIGHT-1) {
                    MapTile southTile = House.mapTiles[y+1][x];
                    boolean southValid = false;
                    for (Class<? extends Room> neighborType : southTile.getEntropy()) {
                        if (Arrays.asList(neighborType.getInterfaces()).contains(NorthPassage.class)) {
                            southValid = true;
                            break;
                        }
                    }

                    if (!southValid) {
                        entropy.remove(roomClass);
                    }
                }
                else if (!(northNeighbor instanceof SouthPassage) || y >= Constants.MAP_HEIGHT){
                    entropy.remove(roomClass);
                }
            }

            Room westNeighbor = House.findRoomByCoordinates(x-1, y);
            if (interfaces.contains(WestPassage.class)) {
                if (westNeighbor == null && x > 0) {
                    MapTile westTile = House.mapTiles[y][x-1];
                    boolean westValid = false;
                    for (Class<? extends Room> neighborType : westTile.getEntropy()) {
                        if (Arrays.asList(neighborType.getInterfaces()).contains(EastPassage.class)) {
                            westValid = true;
                            break;
                        }
                    }

                    if (!westValid) {
                        entropy.remove(roomClass);
                    }
                }
                else  if (!(northNeighbor instanceof SouthPassage) || x < 1){
                    entropy.remove(roomClass);
                }
            }
        }
    }
}
