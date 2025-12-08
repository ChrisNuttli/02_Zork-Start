package ch.bbw.zork.Items;

import ch.bbw.zork.Container;
import ch.bbw.zork.Furniture;
import ch.bbw.zork.Game;
import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.enums.ItemData;
import ch.bbw.zork.interfaces.Hidden;
import ch.bbw.zork.interfaces.HidingSpot;
import ch.bbw.zork.interfaces.Storage;
import ch.bbw.zork.interfaces.Uncover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class Item implements Hidden {
    private String itemID;
	private final String name;
	private final String description;
    private final int space;
    private final double weight;
    private boolean uncovered = true;
    private HashSet<Uncover> uncovers;
    private ItemData itemData;

    public Item(ItemData itemData) {
        this(itemData.name(), itemData.getDescription(), itemData.getSpace(), itemData.getWeight());
        this.itemData = itemData;
    }

	protected Item(String name, String description, int space, double weight) {
		this.name = name;
		this.description = description;
        this.space = space;
        this.weight = weight;

        Game.getHouse().addItem(this);
	}

    public String getName() {
        return this.name;
    }

    @Override
    public HashSet<Uncover> getUncoverItems() {
        return this.uncovers;
    }

    @Override
    public void addUncoverItem(Uncover uncover) {
        this.uncovers.add(uncover);
        this.uncovered = false;
    }

    @Override
    public boolean isUncovered() {
        return this.uncovered;
    }


    @Override
    public void tryUncover(Uncover uncover) {
        if (this.uncovered) { return; }
        boolean result = this.uncovers.contains(uncover);
        if (result) {
            this.uncovered = true;
        }
    }

    @Override
    public void tryUncover(HashSet<Uncover> uncovers) {
        for (Uncover uncover : uncovers) {
            tryUncover(uncover);
        }
    }

    @Override
    public void generateLocationNote(HidingSpot hidingSpot) {
        if (hidingSpot instanceof Furniture) {
            throw new UnsupportedOperationException("Cannot hide Item in Furniture!");
        }

        hidingSpot.generateLocationNote(this);
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

    public void setItemID(String id) {
        this.itemID = id;
    }

    public ItemData getItemData() {
        return itemData;
    }
}
