package ch.bbw.zork.Rooms;

import ch.bbw.zork.*;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.NorthPassage;

public class FrontYard extends Room implements NorthPassage {
    private Transition transitionNorth;

    public FrontYard() {
        super("Front Yard", ""); // TODO: Add Description
        this.setCoordinates(Math.floorDiv(Constants.MAP_WIDTH, 2), Constants.MAP_HEIGHT - 1);
        initialize();

        generateFurniture();
    }

    private void generateFurniture() {
        // TODO: Add furniture
        this.addFurniture(new Furniture("", "", false, true));
    }

    private void initialize() {
        this.addPossibleNeighbor(Direction.NORTH,"Kitchen");
        this.addPossibleNeighbor(Direction.NORTH,"Corridor");
    }

    @Override
    public Transition getPassageNorth() {
        return transitionNorth;
    }

    @Override
    public void setPassageNorth(Transition transitionNorth) {
        this.transitionNorth = transitionNorth;
    }

    @Override
    public Room getNeighborNorth() {
        return this.getNeighbor(Direction.NORTH);
    }

    @Override
    public int[] getCoordinatesNorth() {
        return new int[]{ this.getX(), this.getY()-1 };
    }

//    public String[] getMapPiece() {
//        boolean playerInRoom = Game.house.getPlayerLocation() == this;
//        String[] mapPiece = new String[ROOM_HEIGHT];
//        mapPiece[0] = HORIZONTAL_WALL_OPEN_DOOR;
//        for (int i = 1; i < ROOM_HEIGHT - 1; i++) {
//            if (ROOM_NAME_LINE == i) {
//                mapPiece[i] = FLOOR_LINE('#', '#', this.getName());
//            } else if (PLAYER_LINE == i && playerInRoom) {
//                mapPiece[i] = FLOOR_LINE('#', '#', PLAYER_CHAR);
//            } else {
//                mapPiece[i] = FLOOR_LINE('#', '#');
//            }
//        }
//
//        mapPiece[mapPiece.length - 1] = HORIZONTAL_WALL_PLAIN;
//        return mapPiece;
//    }
//        boolean playerInRoom = Game.house.getPlayerLocation() == this;
//        String name = this.getName();

//        if (name.length() % 2 == 0) {
//            name += " ";
//        }
//
//        String[] mapPiece = new String[ROOM_HEIGHT];
//        String door = new String(new char[3]).replace('\0', DOOR_OPEN_CHAR);
//        String halfWall = new String(new char[Math.floorDiv(ROOM_WIDTH - door.length(),2)]).replace('\0', WALL_CHAR);
//        mapPiece[0] = halfWall + door +  halfWall;
//
//        for (int i = 1; i < ROOM_HEIGHT - 1; i++) {
//            if (Math.floorDiv(ROOM_HEIGHT - door.length(),2) == i) {
//                String halfFloor = new String(new char[Math.floorDiv((ROOM_WIDTH-2) - name.length(),2)]).replace('\0', ' ');
//                mapPiece[i]  = String.format("%s%" + Math.floorDiv((ROOM_WIDTH - name.length()), 2) + "s", WALL_CHAR + halfFloor + name, WALL_CHAR);
//            }
//            else if (Math.floorDiv(ROOM_HEIGHT - door.length(),2) + 1 == i && playerInRoom) {
//                String halfFloor = new String(new char[Math.floorDiv((ROOM_WIDTH-2),2)]).replace('\0', ' ');
//                mapPiece[i]  = String.format("%s%" + (Math.floorDiv((ROOM_WIDTH - 1), 2)-1) + "s", WALL_CHAR + halfFloor + PLAYER_CHAR, WALL_CHAR);
//            }
//            else {
//                mapPiece[i]  = WALL_CHAR + new String(new char[ROOM_WIDTH - 2]).replace('\0', ' ') + WALL_CHAR;
//            }
//        }
//        mapPiece[ROOM_HEIGHT-1] = new String(new char[ROOM_WIDTH]).replace('\0', WALL_CHAR);
//
//        return mapPiece;
//    }
}
