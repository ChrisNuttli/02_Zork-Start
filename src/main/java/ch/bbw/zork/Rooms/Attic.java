package ch.bbw.zork.Rooms;

import ch.bbw.zork.*;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.SouthPassage;

import java.util.Random;

public class Attic extends Room implements SouthPassage {
	private Passage passageSouth;

	public Attic() {
		super("Attic", ""); // TODO: Add Description
		this.addPossibleNeighbor(Direction.SOUTH,"Corridor");
		this.addPossibleNeighbor(Direction.SOUTH,"LivingRoom");

		generateFurniture();
	}

	private void generateFurniture() {
		this.addFurniture(new Furniture("Boxes", "", false, true));
		this.addFurniture(new Furniture("Chairs", "", false, false));
		this.addFurniture(new Furniture("Table", "", true, true));
		this.addFurniture(new Furniture("Floor", "", false, false));
	}

	public Passage getPassageSouth() {
		return passageSouth;
	}

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
