package ch.bbw.zork.Rooms;

import ch.bbw.zork.House;
import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.WestPassage;

public class DiningRoom extends Room implements EastPassage, WestPassage {
	private Passage passageEast;
	private Passage passageWest;

	public DiningRoom() {
		super("DiningRoom", ""); // TODO: Add Description
		initialize();
	}

	private void initialize() {
		this.addPossibleNeighbor(Direction.EAST,"Kitchen");
		this.addPossibleNeighbor(Direction.EAST,"Corridor"); // TODO: Add this connection to the floorplan

		this.addPossibleNeighbor(Direction.WEST,"LivingRoom");
		this.addPossibleNeighbor(Direction.WEST,"Office");
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
