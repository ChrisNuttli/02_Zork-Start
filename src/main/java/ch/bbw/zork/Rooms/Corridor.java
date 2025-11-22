package ch.bbw.zork.Rooms;

import ch.bbw.zork.*;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.EastPassage;
import ch.bbw.zork.interfaces.NorthPassage;
import ch.bbw.zork.interfaces.SouthPassage;
import ch.bbw.zork.interfaces.WestPassage;

import static ch.bbw.zork.Constants.FLOOR_LINE;

public class Corridor extends Room implements NorthPassage, EastPassage, SouthPassage, WestPassage {
	private Transition transitionNorth;
	private Transition transitionEast;
	private Transition transitionSouth;
	private Transition transitionWest;

	public Corridor() {
		super("Corridor", ""); // TODO: Add Description
		initialize();

		generateFurniture();
	}

	private void generateFurniture() {
		// TODO: Add furniture
		this.addFurniture(new Furniture("", "", false, true));
	}

	private void initialize() {
		this.addPossibleNeighbor(Direction.NORTH,"Attic");
		this.addPossibleNeighbor(Direction.NORTH,"Kitchen");
		this.addPossibleNeighbor(Direction.NORTH,"Office");
		this.addPossibleNeighbor(Direction.NORTH,"Bedroom");

		this.addPossibleNeighbor(Direction.EAST,"Kitchen");
		this.addPossibleNeighbor(Direction.EAST,"LivingRoom");
		this.addPossibleNeighbor(Direction.EAST,"Bathroom");
		this.addPossibleNeighbor(Direction.EAST,"DiningRoom");

		this.addPossibleNeighbor(Direction.SOUTH,"Cellar");
		this.addPossibleNeighbor(Direction.SOUTH,"FrontYard");

		this.addPossibleNeighbor(Direction.WEST,"Basement");
		this.addPossibleNeighbor(Direction.WEST,"LivingRoom");
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
