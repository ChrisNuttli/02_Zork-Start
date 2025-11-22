package ch.bbw.zork.Rooms;

import ch.bbw.zork.*;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.WestPassage;

import static ch.bbw.zork.Constants.*;
import static ch.bbw.zork.Constants.FLOOR_LINE;
import static ch.bbw.zork.Constants.PLAYER_CHAR;
import static ch.bbw.zork.Constants.PLAYER_LINE;

public class Bathroom extends Room implements WestPassage {
	private Transition transitionWest;

	public Bathroom() {
		super("Bathroom", ""); // TODO: Add Description
		this.addPossibleNeighbor(Direction.WEST, "Bedroom");
		this.addPossibleNeighbor(Direction.WEST, "Corridor");
		this.addPossibleNeighbor(Direction.WEST, "LivingRoom");

		generateFurniture();
	}

	private void generateFurniture() {
		// TODO: Add furniture
		this.addFurniture(new Furniture("", "", false, true));
	}

	@Override
	public Transition getPassageWest() {
		return transitionWest;
	}

	@Override
	public void setPassageWest(Transition transitionWest) {
		this.transitionWest = transitionWest;
	}

	@Override
	public Room getNeighborWest() {
		Room result = null;
		int[] westCoords = this.getCoordinatesWest();
		for (Room room : House.roomList) {
			if (room.getX() == westCoords[0] && room.getY() == westCoords[1]) result = room;
		}

		return result;
	}

	@Override
	public int[] getCoordinatesWest() {
		return new int[]{ this.getX()-1, this.getY() };
	}

    public String[] getMapPiece() {
        boolean playerInRoom = Game.house.getPlayerLocation() == this;
        String[] mapPiece = new String[ROOM_HEIGHT];
        mapPiece[0] = HORIZONTAL_WALL_PLAIN;
        char westDoorChar = DOOR_OPEN_CHAR;
        if (this.transitionWest != null && this.transitionWest.isLocked()) {
            westDoorChar = DOOR_LOCKED_CHAR;
        }

        for (int i = 1; i < ROOM_HEIGHT - 1; i++) {
            if (ROOM_NAME_LINE - 1 == i) {
                mapPiece[i] = FLOOR_LINE(westDoorChar, '#');
            }
            else if (ROOM_NAME_LINE == i) {
                mapPiece[i] = FLOOR_LINE(westDoorChar, '#', this.getName());
            } else if (PLAYER_LINE == i && playerInRoom) {
                mapPiece[i] = FLOOR_LINE(westDoorChar, '#', PLAYER_CHAR);
            } else {
                mapPiece[i] = FLOOR_LINE('#', '#');
            }
        }

        mapPiece[mapPiece.length - 1] = HORIZONTAL_WALL_PLAIN;
        return mapPiece;
    }
}
