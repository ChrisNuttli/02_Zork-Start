package ch.bbw.zork.interfaces;

import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;

public interface EastPassage {
	public Passage getPassageEast();
	public void setPassageEast(Passage passage);
	public Room getNeighborEast();
	public int[] getCoordinatesEast();
}
