package ch.bbw.zork.furniture;

import ch.bbw.zork.interfaces.Lockable;

public class Cupboard extends Furniture implements Lockable {
	public Cupboard(String name, String description) {
		super(name, description);
	}
}
