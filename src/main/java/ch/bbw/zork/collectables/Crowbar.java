package ch.bbw.zork.collectables;

import ch.bbw.zork.interfaces.Lockable;
import ch.bbw.zork.interfaces.Unlocking;

public class Crowbar extends Collectable implements Unlocking {
	private final Lockable[] unlockedFurnature;
	public Crowbar(String name, String description, Lockable... unlockedFurnature) {
		super(name, description);
		this.unlockedFurnature = unlockedFurnature;
	}

	@Override
	public Lockable[] getUnlockedFurniture() {
		return unlockedFurnature;
	}
}
