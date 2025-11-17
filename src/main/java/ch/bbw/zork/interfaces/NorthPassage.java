package ch.bbw.zork.interfaces;

import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;

public interface NorthPassage {
	public Passage getPassageNorth();
	public void setPassageNorth(Passage passage);
	public Room getNeighborNorth();
	public int[] getCoordinatesNorth();
}
