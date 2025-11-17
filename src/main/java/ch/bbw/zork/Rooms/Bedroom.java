package ch.bbw.zork.Rooms;

import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.SouthPassage;

public class Bedroom extends Room implements EastPassage, SouthPassage {
	private Passage passageEast;
	private Passage passageSouth;

	public Bedroom() {
		super("Bedroom", ""); // TODO: Add Description
		initialize();
	}

	private void initialize() {
		this.addPossibleNeighbor(Direction.EAST,"Office");
		this.addPossibleNeighbor(Direction.EAST,"Bathroom");
		this.addPossibleNeighbor(Direction.SOUTH,"LivingRoom");
		this.addPossibleNeighbor(Direction.SOUTH,"Corridor");
	}

	@Override
	public Passage getPassageEast() {
		return passageEast;
	}

	@Override
	public void setPassageEast(Passage passageEast) {
		this.passageEast = passageEast;
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
}
