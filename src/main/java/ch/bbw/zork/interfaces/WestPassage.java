package ch.bbw.zork.interfaces;

import ch.bbw.zork.Transition;
import ch.bbw.zork.Room;

public interface WestPassage extends Passage{
	public Transition getPassageWest();
	public void setPassageWest(Transition transition);
	public Room getNeighborWest();
	public int[] getCoordinatesWest();
}
