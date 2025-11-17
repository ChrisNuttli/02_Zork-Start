package ch.bbw.zork.Rooms;

import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.NorthPassage;

public class Cellar extends Room implements NorthPassage {
	private Passage passageNorth;

	public Cellar() {
		super("Cellar", ""); // TODO: Add Description
		initialize();
	}

	private void initialize() {
		this.addPossibleNeighbor(Direction.NORTH,"");
	}

	@Override
	public Passage getPassageNorth() {
		return passageNorth;
	}

	@Override
	public void setPassageNorth(Passage passageNorth) {
		this.passageNorth = passageNorth;
	}

	@Override
	public Room getNeighborNorth() {
		return this.getNeighbor(Direction.NORTH);
	}

	@Override
	public int[] getCoordinatesNorth() {
		return new int[]{ this.getX(), this.getY()-1 };
	}
}
