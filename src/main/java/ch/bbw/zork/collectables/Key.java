package ch.bbw.zork.collectables;

import ch.bbw.zork.interfaces.Lockable;
import ch.bbw.zork.interfaces.Unlocking;

public class Key extends Collectable implements Unlocking {
	private Lockable[] unlockedFurnature;
	public Key(String name, String description, Lockable... unlockedFurnature) {
		super(name, description);
		this.unlockedFurnature = unlockedFurnature;
	}

	@Override
	public Lockable[] getUnlockedFurniture() {
		return unlockedFurnature;
	}
}
