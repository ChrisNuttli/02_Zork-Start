package ch.bbw.zork.interfaces;

import ch.bbw.zork.Transition;
import ch.bbw.zork.Room;

public interface EastPassage extends Passage {
	public Transition getPassageEast();
	public void setPassageEast(Transition transition);
	public Room getNeighborEast();
	public int[] getCoordinatesEast();
}
