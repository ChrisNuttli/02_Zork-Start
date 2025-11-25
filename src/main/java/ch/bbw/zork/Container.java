package ch.bbw.zork;

import ch.bbw.zork.Items.Item;
import ch.bbw.zork.interfaces.Storage;
import ch.bbw.zork.interfaces.Uncover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.UUID;

public class Container implements Storage {
    private final String storageID;
    private final HashMap<String, Item> contents;
    private final int spaceLimit;
    private final int slots;
    private final double weightLimit;
    private Lock lock;
    private ArrayList<Item> checkedItems;

    public Container() {
        this(Integer.MAX_VALUE, Double.MAX_VALUE);
    }

    public Container(int spaceLimit, double weightLimit) {
        this(Integer.MAX_VALUE, Double.MAX_VALUE, Integer.MAX_VALUE);
    }

    public Container(int spaceLimit, double weightLimit, int slots) {
        this.spaceLimit = spaceLimit;
        this.weightLimit = weightLimit;
        this.contents = new HashMap<>();
        this.storageID = UUID.randomUUID().toString();
        this.slots = slots;
        this.checkedItems = new ArrayList<>();
    }

    @Override
    public HashMap<String, Item> getContents() {
        return this.contents;
    }

    @Override
    public void stashItem(Item item) {
        if (this.slots - this.getContents().size() < 0) {
            throw new IllegalStateException("Cannot hold any more items");
        }

        if (getAvailableSpace() < item.getSpace()) {
            throw new IllegalStateException("Cannot stash this item. Not enough space.");
        }

        if (getAvailableWeight() < item.getWeight()) {
            throw new IllegalStateException("This Item is to heavy to stash in here");
        }

        contents.put(item.getItemID(), item);
    }

    @Override
    public Item fetchItem(String id) {
        Item item = contents.get(id);
        if (item == null) {
            throw new InputMismatchException("There is no item with the provided id");
        }

        return item;
    }

    public Item fetchItem() {
        if (contents.isEmpty()) {
            throw new IllegalStateException("No item was found");
        }

        if (contents.size() > 1) {
            throw new IllegalStateException("More than one item was found. please provide an itemID");
        }

        return contents.values().iterator().next();
    }

    @Override
    public int getSpaceLimit() {
        return this.spaceLimit;
    }

    @Override
    public double getWeightLimit() {
        return weightLimit;
    }

    public int getAvailableSpace() {
        int usedSpace = 0;
        for (String id : contents.keySet()) {
            Item item = contents.get(id);
            usedSpace += item.getSpace();
        }

        return this.getSpaceLimit() - usedSpace;
    }

    public double getAvailableWeight() {
        double usedWeight = 0;
        for (String id : contents.keySet()) {
            Item item = contents.get(id);
            usedWeight += item.getWeight();
        }

        return this.getWeightLimit() - usedWeight;
    }

    public ArrayList<Item> check(ArrayList<Uncover> uncovers) {
        ArrayList<Item> uncoveredItems = new ArrayList<>();
        for (Item item : getContents().values()) {
            if (item.tryUncover(uncovers)) {
                uncoveredItems.add(item);
            }
        }

        this.checkedItems = uncoveredItems;
        return uncoveredItems;
    }

    public String getStorageID() {
        return storageID;
    }

    public Lock getLock() {
        return this.lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public ArrayList<Item> getCheckedItems() {
        return checkedItems;
    }
}
