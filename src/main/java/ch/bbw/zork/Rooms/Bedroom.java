package ch.bbw.zork.Rooms;

import ch.bbw.zork.Furniture;
import ch.bbw.zork.Transition;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.SouthPassage;

import static ch.bbw.zork.Constants.FLOOR_LINE;

public class Bedroom extends Room implements EastPassage, SouthPassage {
	private Transition transitionEast;
	private Transition transitionSouth;

	public Bedroom() {
		super("Bedroom", ""); // TODO: Add Description
		this.addPossibleNeighbor(Direction.EAST,"Office");
		this.addPossibleNeighbor(Direction.EAST,"Bathroom");
		this.addPossibleNeighbor(Direction.SOUTH,"LivingRoom");
		this.addPossibleNeighbor(Direction.SOUTH,"Corridor");

		generateFurniture();
	}

	private void generateFurniture() {
		// TODO: Add furniture
		this.addFurniture(new Furniture("", "", false, true));
	}

	@Override
	public Transition getPassageEast() {
		return transitionEast;
	}

	@Override
	public void setPassageEast(Transition transitionEast) {
		this.transitionEast = transitionEast;
	}

	@Override
	public Room getNeighborEast() {
		return this.getNeighbor(Direction.EAST);
	}

	@Override
	public int[] getCoordinatesEast() {
		return new int[]{ this.getX()+1, this.getY() };
	}

	@Override
	public Transition getPassageSouth() {
		return transitionSouth;
	}

	@Override
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
//        char eastDoorChar = DOOR_OPEN_CHAR;
//        if (this.passageEast != null && this.passageEast.isLocked()) {
//            eastDoorChar = DOOR_LOCKED_CHAR;
//        }
//
//        for (int i = 1; i < ROOM_HEIGHT - 1; i++) {
//            if (ROOM_NAME_LINE - 1 == i) {
//                mapPiece[i] = FLOOR_LINE('#', eastDoorChar);
//            }
//            else if (ROOM_NAME_LINE == i) {
//                mapPiece[i] = FLOOR_LINE('#', eastDoorChar, this.getName());
//            } else if (PLAYER_LINE == i && playerInRoom) {
//                mapPiece[i] = FLOOR_LINE('#', eastDoorChar, PLAYER_CHAR);
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
