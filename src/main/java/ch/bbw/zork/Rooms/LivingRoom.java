package ch.bbw.zork.Rooms;

import ch.bbw.zork.Furniture;
import ch.bbw.zork.Transition;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.NorthPassage;
import ch.bbw.zork.interfaces.WestPassage;

public class LivingRoom extends Room implements NorthPassage, EastPassage, WestPassage {
	private Transition transitionNorth;
	private Transition transitionEast;
	private Transition transitionWest;

	public LivingRoom() {
		super("LivingRoom", ""); // TODO: Add Description
		initialize();

		generateFurniture();
	}

	private void generateFurniture() {
		// TODO: Add furniture
		this.addFurniture(new Furniture("", "", false, true));
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
	public Transition getPassageWest() {
		return transitionWest;
	}

	@Override
	public void setPassageWest(Transition transitionWest) {
		this.transitionWest = transitionWest;
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
