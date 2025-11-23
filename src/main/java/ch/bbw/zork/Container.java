package ch.bbw.zork;

import ch.bbw.zork.Items.Item;
import ch.bbw.zork.interfaces.Storage;

import java.util.HashMap;
import java.util.UUID;

public class Container implements Storage {
    private final String storageID;
    private final HashMap<String, Item> contents;
    private final int spaceLimit;
    private final double weightLimit;
    private Lock lock;

    public Container() {
        this(Integer.MAX_VALUE, Double.MAX_VALUE);
    }

    public Container(int spaceLimit, double weightLimit) {
        this.spaceLimit = spaceLimit;
        this.weightLimit = weightLimit;
        this.contents = new HashMap<>();
        this.storageID = UUID.randomUUID().toString();
    }

    @Override
    public HashMap<String, Item> getContents() {
        return this.contents;
    }

    @Override
    public void stashItem(Item item) {
        if (getAvailableSpace() < item.getSpace()) {
            throw new RuntimeException("Cannot stash this item. Not enough space.");
        }

        if (getAvailableWeight() < item.getWeight()) {
            throw new RuntimeException("This Item is to heavy to stash in here");
        }

        contents.put(item.getItemID(), item);
    }

    @Override
    public Item fetchItem(String id) {
        Item item = contents.get(id);
        if (item == null) {
            throw new RuntimeException("There is no item with the provided id");
        }

        return item;
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

    public String getStorageID() {
        return storageID;
    }

    public Lock getLock() {
        return this.lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
