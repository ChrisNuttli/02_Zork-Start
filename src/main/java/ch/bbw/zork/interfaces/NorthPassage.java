package ch.bbw.zork.interfaces;

import ch.bbw.zork.Door;
import ch.bbw.zork.Room;

public interface NorthPassage extends Passage{
	public Door getPassageNorth();
	public void setPassageNorth(Door door);
	public Room getNeighborNorth();
	public int[] getCoordinatesNorth();
}
