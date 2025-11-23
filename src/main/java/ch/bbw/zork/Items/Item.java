package ch.bbw.zork.Items;

import ch.bbw.zork.Game;
import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.interfaces.Hidden;

import java.util.ArrayList;
import java.util.UUID;

public class Item implements Hidden {
	private final String name;
	private final String description;
    private final String itemID;
    private final int space;
    private final double weight;
    private boolean uncovered = true;
    private ArrayList<String> uncoverIDs;

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

    @Override
    public ArrayList<String> getUncoverIDs() {
        return this.uncoverIDs;
    }

    @Override
    public void addUncoverID(String uncoverID) {
        this.uncoverIDs.add(uncoverID);
        this.uncovered = false;
    }

    @Override
    public boolean getIsUncovered() {
        return this.uncovered;
    }

    @Override
    public void setIsUncovered(boolean isUncovered) {
        this.uncovered = isUncovered;
    }

    @Override
    public boolean tryUncover(String uncoverID) {
        if (this.uncovered) { return true; }
        boolean result = this.uncoverIDs.contains(uncoverID);
        if (result) {
            this.uncovered = true;
        }
        return result;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public LocationNote generatLocationNote(FurnitureData hidingSpot) {
        return new LocationNote("", LocationNote.getRandomText(hidingSpot, this));
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
