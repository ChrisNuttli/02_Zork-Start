package ch.bbw.zork.collectables;

import ch.bbw.zork.interfaces.Lockable;
import ch.bbw.zork.interfaces.Unlocking;

public class CombinationLockNote extends Note implements Unlocking {
	private final Lockable[] unlockedFurnature;

	public CombinationLockNote(String name, String description, String content, Lockable... unlockedFurnature) {
		super(name, description, content);
		this.unlockedFurnature = unlockedFurnature;
	}

	@Override
	public Lockable[] getUnlockedFurniture() {
		return unlockedFurnature;
	}
}
