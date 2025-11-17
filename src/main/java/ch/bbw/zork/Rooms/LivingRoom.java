package ch.bbw.zork.Rooms;

import ch.bbw.zork.House;
import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.NorthPassage;
import ch.bbw.zork.interfaces.WestPassage;

public class LivingRoom extends Room implements NorthPassage, EastPassage, WestPassage {
	private Passage passageNorth;
	private Passage passageEast;
	private Passage passageWest;

	public LivingRoom() {
		super("LivingRoom", ""); // TODO: Add Description
		initialize();
	}

	private void initialize() {
		this.addPossibleNeighbor(Direction.NORTH,"Office");
		this.addPossibleNeighbor(Direction.NORTH,"Bedroom");
		this.addPossibleNeighbor(Direction.NORTH,"Attic");

		this.addPossibleNeighbor(Direction.EAST,"Corridor");
		this.addPossibleNeighbor(Direction.EAST,"Bathroom");

		this.addPossibleNeighbor(Direction.WEST,"Corridor");
		this.addPossibleNeighbor(Direction.WEST,"Kitchen");
		this.addPossibleNeighbor(Direction.WEST,"DiningRoom");

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
