package ch.bbw.zork.interfaces;

import ch.bbw.zork.Transition;
import ch.bbw.zork.Room;

public interface SouthPassage extends Passage{
	public Transition getPassageSouth();
	public void setPassageSouth(Transition transition);
	public Room getNeighborSouth();
	public int[] getCoordinatesSouth();
}
