package ch.bbw.zork.interfaces;

import ch.bbw.zork.Door;
import ch.bbw.zork.Room;

public interface WestPassage extends Passage{
	public Door getPassageWest();
	public void setPassageWest(Door door);
	public Room getNeighborWest();
	public int[] getCoordinatesWest();
}
