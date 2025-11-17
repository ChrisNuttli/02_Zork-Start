package ch.bbw.zork.interfaces;

import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;

public interface SouthPassage {
	public Passage getPassageSouth();
	public void setPassageSouth(Passage passage);
	public Room getNeighborSouth();
	public int[] getCoordinatesSouth();
}
