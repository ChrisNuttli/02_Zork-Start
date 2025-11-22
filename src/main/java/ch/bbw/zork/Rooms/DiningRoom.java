package ch.bbw.zork.Rooms;

import ch.bbw.zork.Furniture;
import ch.bbw.zork.Transition;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.WestPassage;

public class DiningRoom extends Room implements EastPassage, WestPassage {
	private Transition transitionEast;
	private Transition transitionWest;

	public DiningRoom() {
		super("DiningRoom", ""); // TODO: Add Description
		initialize();

		generateFurniture();
	}

	private void generateFurniture() {
		// TODO: Add furniture
		this.addFurniture(new Furniture("", "", false, true));
	}

	private void initialize() {
		this.addPossibleNeighbor(Direction.EAST,"Kitchen");
		this.addPossibleNeighbor(Direction.EAST,"Corridor"); // TODO: Add this connection to the floorplan

		this.addPossibleNeighbor(Direction.WEST,"LivingRoom");
		this.addPossibleNeighbor(Direction.WEST,"Office");
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
