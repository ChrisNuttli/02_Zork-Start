package ch.bbw.zork.interfaces;

import ch.bbw.zork.Transition;
import ch.bbw.zork.Room;

public interface NorthPassage extends Passage{
	public Transition getPassageNorth();
	public void setPassageNorth(Transition transition);
	public Room getNeighborNorth();
	public int[] getCoordinatesNorth();
}
