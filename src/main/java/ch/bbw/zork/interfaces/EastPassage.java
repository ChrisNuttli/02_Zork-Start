package ch.bbw.zork.interfaces;

import ch.bbw.zork.Door;
import ch.bbw.zork.Room;

public interface EastPassage extends Passage {
	public Door getPassageEast();
	public void setPassageEast(Door door);
	public Room getNeighborEast();
	public int[] getCoordinatesEast();
}
