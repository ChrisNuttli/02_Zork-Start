package ch.bbw.zork.Items;

import ch.bbw.zork.Game;

import java.util.UUID;

public class Item {
	private final String name;
	private final String description;
    private final String itemID;
    private final int space;
    private final double weight;

    protected Item(String name, String description) {
        this(name, description, 0, 0);
    }

	public Item(String name, String description, int space, double weight) {
		this.name = name;
		this.description = description;
        this.space = space;
        this.weight = weight;
        this.itemID = UUID.randomUUID().toString();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

    public int getSpace() {
        return space;
    }

    public double getWeight() {
        return weight;
    }

    public String getItemID() {
        return itemID;
    }
}
