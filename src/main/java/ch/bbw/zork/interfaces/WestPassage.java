package ch.bbw.zork.interfaces;

import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;

public interface WestPassage {
	public Passage getPassageWest();
	public void setPassageWest(Passage passage);
	public Room getNeighborWest();
	public int[] getCoordinatesWest();
}
