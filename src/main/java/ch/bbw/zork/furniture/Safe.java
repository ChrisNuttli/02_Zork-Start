package ch.bbw.zork.furniture;

import ch.bbw.zork.interfaces.Lockable;

public class Safe extends Furniture implements Lockable {
	public Safe(String name, String description) {
		super(name, description);
	}
}
