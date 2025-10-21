package ch.bbw.zork.collectables;

import ch.bbw.zork.furniture.Furniture;
import ch.bbw.zork.interfaces.Revealing;

public class ItemLocationNote extends Note implements Revealing {
	public ItemLocationNote(String name, String description, String content) {
		super(name, description, content);
	}

	@Override
	public Furniture getRevealedFurniture() {
		return null;
	}

	@Override
	public void setRevealedFurniture(Furniture furniture) {

	}
}
