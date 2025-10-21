package ch.bbw.zork.collectables;

import ch.bbw.zork.furniture.Furniture;
import ch.bbw.zork.interfaces.Revealing;

public class Knife extends Collectable implements Revealing {
	public Knife(String name, String description) {
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
