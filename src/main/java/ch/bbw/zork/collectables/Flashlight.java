package ch.bbw.zork.collectables;

import ch.bbw.zork.furniture.Furniture;
import ch.bbw.zork.interfaces.Revealing;

public class Flashlight extends Collectable implements Revealing {
	public Flashlight(String name, String description) {
		super(name, description);
	}

	@Override
	public Furniture getRevealedFurniture() {
		return null;
	}

	@Override
	public void setRevealedFurniture(Furniture furniture) {

	}
}
