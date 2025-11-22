package ch.bbw.zork.Rooms;

import ch.bbw.zork.Furniture;
import ch.bbw.zork.Transition;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.SouthPassage;
import ch.bbw.zork.interfaces.WestPassage;

public class Office extends Room implements SouthPassage, WestPassage {
	private Transition transitionSouth;
	private Transition transitionWest;

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
        this.addPossibleNeighbor(Direction.WEST,"Kitchen");
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
