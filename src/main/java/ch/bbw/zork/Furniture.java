package ch.bbw.zork;

import java.util.ArrayList;
import java.util.UUID;

public class Furniture {
    private final String ID;
	private final String name;
	private final String description;
	private ArrayList<Collectable> items;
	private Safe safe;
	private final boolean canHideSafe;
	private final boolean canHoldItems;

	// TODO: Implement class
	public Furniture(String name, String description, boolean canHideSafe, boolean canHoldItems) {
        this.ID = UUID.randomUUID().toString();
		this.name = name;
		this.description = description;
		this.items = new ArrayList<>();
		this.canHideSafe = canHideSafe;
		this.canHoldItems = canHoldItems;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<Collectable> getItems() {
		return this.items;
	}

	public void addItem(Collectable item) {
		this.items.add(item);
	}

	public Safe getSafe() {
		return safe;
	}

	public void setSafe(Safe safe) {
		this.safe = safe;
	}

	public boolean isCanHideSafe() {
		return canHideSafe;
	}

	public boolean isCanHoldItems() {
		return canHoldItems;
	}
}
