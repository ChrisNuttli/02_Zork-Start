package ch.bbw.zork.Rooms;

import ch.bbw.zork.Furniture;
import ch.bbw.zork.Transition;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.SouthPassage;
import ch.bbw.zork.interfaces.WestPassage;

public class Kitchen extends Room implements EastPassage, SouthPassage, WestPassage {
	private Transition transitionEast;
	private Transition transitionSouth;
	private Transition transitionWest;

	public Kitchen() {
		super("Kitchen", ""); // TODO: Add Description
		initialize();

		generateFurniture();
	}

	private void generateFurniture() {
		// TODO: Add furniture
		this.addFurniture(new Furniture("", "", false, true));
	}

	private void initialize() {
		this.addPossibleNeighbor(Direction.EAST,"LivingRoom");
		this.addPossibleNeighbor(Direction.EAST,"DiningRoom");
        this.addPossibleNeighbor(Direction.EAST,"Office");

		this.addPossibleNeighbor(Direction.SOUTH,"FrontYard");
		this.addPossibleNeighbor(Direction.SOUTH,"Cellar");
		this.addPossibleNeighbor(Direction.SOUTH,"Corridor");

		this.addPossibleNeighbor(Direction.WEST,"Corridor");
		this.addPossibleNeighbor(Direction.WEST,"Attic");
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
