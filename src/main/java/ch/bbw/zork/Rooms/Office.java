package ch.bbw.zork.Rooms;

import ch.bbw.zork.Furniture;
import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.SouthPassage;
import ch.bbw.zork.interfaces.WestPassage;

public class Office extends Room implements SouthPassage, WestPassage {
	private Passage passageSouth;
	private Passage passageWest;

	public Office() {
		super("Office", ""); // TODO: Add Description
		initializePossibleNeighbors();
		initializeFurniture();
	}

	private void initializeFurniture() {
		// TODO: Add furniture
		this.addFurniture(new Furniture("", "", false, true));
	}

	private void initializePossibleNeighbors() {
		this.addPossibleNeighbor(Direction.SOUTH,"LivingRoom");
		this.addPossibleNeighbor(Direction.SOUTH,"Corridor");
		this.addPossibleNeighbor(Direction.WEST,"Bedroom");
		this.addPossibleNeighbor(Direction.WEST,"DiningRoom");
	}

	@Override
	public Passage getPassageSouth() {
		return passageSouth;
	}

	@Override
	public void setPassageSouth(Passage passageSouth) {
		this.passageSouth = passageSouth;
	}

	@Override
	public Room getNeighborSouth() {
		return this.getNeighbor(Direction.SOUTH);
	}

	@Override
	public int[] getCoordinatesSouth() {
		return new int[]{ this.getX(), this.getY()+1 };
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
		return this.getNeighbor(Direction.WEST);
	}

	@Override
	public int[] getCoordinatesWest() {
		return new int[]{ this.getX()-1, this.getY() };
	}
}
