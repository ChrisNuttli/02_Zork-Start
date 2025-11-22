package ch.bbw.zork.Rooms;

import ch.bbw.zork.*;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.SouthPassage;

public class Attic extends Room implements SouthPassage {
	private Transition transitionSouth;

	public Attic() {
		super("Attic", ""); // TODO: Add Description
		this.addPossibleNeighbor(Direction.SOUTH,"Corridor");
		this.addPossibleNeighbor(Direction.SOUTH,"LivingRoom");

		generateFurniture();
	}

	private void generateFurniture() {
		this.addFurniture(new Furniture("Boxes", "", false, true));
		this.addFurniture(new Furniture("Chairs", "", false, false));
		this.addFurniture(new Furniture("Table", "", true, true));
		this.addFurniture(new Furniture("Floor", "", false, false));
	}

	public Transition getPassageSouth() {
		return transitionSouth;
	}

	public void setPassageSouth(Transition transitionSouth) {
		this.transitionSouth = transitionSouth;
	}

	@Override
	public Room getNeighborSouth() {
		return this.getNeighbor(Direction.SOUTH);
	}

	@Override
	public int[] getCoordinatesSouth() {
		return new int[]{ this.getX(), this.getY()+1 };
	}

//    public String[] getMapPiece() {
//        boolean playerInRoom = Game.house.getPlayerLocation() == this;
//        String[] mapPiece = new String[ROOM_HEIGHT];
//        mapPiece[0] = HORIZONTAL_WALL_PLAIN;
//
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
//        if (this.passageSouth != null && this.passageSouth.isLocked()) {
//            mapPiece[mapPiece.length - 1] = HORIZONTAL_WALL_LOCKED_DOOR;
//        }
//        else {
//            mapPiece[mapPiece.length - 1] = HORIZONTAL_WALL_OPEN_DOOR;
//        }
//
//        return mapPiece;
//    }
}
