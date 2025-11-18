package ch.bbw.zork.Rooms;

import ch.bbw.zork.Furniture;
import ch.bbw.zork.House;
import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.WestPassage;

public class Bathroom extends Room implements WestPassage {
	private Passage passageWest;

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
	public Passage getPassageWest() {
		return passageWest;
	}

	@Override
	public void setPassageWest(Passage passageWest) {
		this.passageWest = passageWest;
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
}
