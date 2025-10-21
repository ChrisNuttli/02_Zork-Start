package ch.bbw.zork.furniture;

import ch.bbw.zork.interfaces.Lockable;

public class Door extends Furniture implements Lockable {

	public Door(String name, String description) {
		super(name, description);
	}
}
