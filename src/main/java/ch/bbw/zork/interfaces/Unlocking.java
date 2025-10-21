package ch.bbw.zork.interfaces;

/**
 * Interface Unlocking - for items and notes that are needed to unlock something.
 *
 * @author Christian Nuttli
 * @version 1.0
 */
public interface Unlocking {
	Lockable[] unlockedFurnature = null;
	public Lockable[] getUnlockedFurniture();
}
