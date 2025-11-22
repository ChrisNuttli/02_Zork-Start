package ch.bbw.zork.interfaces;

import ch.bbw.zork.Door;
import ch.bbw.zork.Room;

public interface SouthPassage extends Passage{
	public Door getPassageSouth();
	public void setPassageSouth(Door door);
	public Room getNeighborSouth();
	public int[] getCoordinatesSouth();
}
