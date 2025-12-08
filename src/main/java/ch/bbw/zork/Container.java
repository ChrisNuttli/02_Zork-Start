package ch.bbw.zork;

import ch.bbw.zork.Items.Item;
import ch.bbw.zork.interfaces.Hidden;
import ch.bbw.zork.interfaces.HidingSpot;
import ch.bbw.zork.interfaces.Storage;
import ch.bbw.zork.interfaces.Uncover;

import java.util.*;

public class Container implements Storage, HidingSpot {
    private String storageID;
    private final String name;
    private final String storageObjectName;
    private final String roomName;
    private final HashMap<String, Item> contents;
    private final int spaceLimit;
    private final int slots;
    private final double weightLimit;
    private Lock lock;
    private ArrayList<Item> checkedItems;

    public Container(String name, String storageObjectName, String roomName) {
        this(Integer.MAX_VALUE, Double.MAX_VALUE, name, storageObjectName, roomName);
    }

    public Container(int spaceLimit, double weightLimit, String name, String storageObjectName, String roomName) {
        this(Integer.MAX_VALUE, Double.MAX_VALUE, Integer.MAX_VALUE, name, storageObjectName, roomName);
    }

    public Container(int spaceLimit, double weightLimit, int slots, String name, String storageObjectName, String roomName) {
        this.spaceLimit = spaceLimit;
        this.weightLimit = weightLimit;
        this.name = name;
        this.storageObjectName = storageObjectName;
        this.roomName = roomName;
        this.contents = new HashMap<>();
        this.slots = slots;
        this.checkedItems = new ArrayList<>();
    }

    @Override
    public HashMap<String, Item> getContents() {
        return this.contents;
    }

    @Override
    public void stashItem(Item item) {
        if (Objects.equals(this.roomName, "Front Yard")) {
            System.out.println();
        }
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

        this.contents.remove(id);
        return item;
    }

    public Item fetchItem() {
        if (contents.isEmpty()) {
            throw new IllegalStateException("No item was found");
        }

        if (contents.size() > 1) {
            throw new IllegalStateException("More than one item was found. please provide an itemID");
        }

        Item item = contents.values().iterator().next();
        contents.remove(contents.keySet().iterator().next());

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

    public ArrayList<Item> check(HashSet<Uncover> uncovers) {
        ArrayList<Item> uncoveredItems = new ArrayList<>();
        for (Item item : getContents().values()) {
            item.tryUncover(uncovers);
            if (item.isUncovered()) {
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

    public HashSet<Item> getCheckedItems() {
        HashSet<Item> result = new HashSet<>();

        for (Item item: contents.values()) {
            if (item.isUncovered()) {
                result.add(item);
            }
        }

        return result;
    }

    public void removeItem(Item item) {
        this.contents.remove(item.getItemID());
    }

    public String getName() {
        return name;
    }

    @Override
    public void generateLocationNote(Hidden hiddenObject) {
        if (!(hiddenObject instanceof Item)) {
            throw new InputMismatchException("Only items can be hidden in Containers.");
        }

        Item hiddenItem = (Item) hiddenObject;
    }

    @Override
    public void tryUncoverHiddenItems(Uncover uncover) {
        // TODO: Implement Method
    }

    @Override
    public String getHidingSpotLabel() {
        return this.getName();
    }

    @Override
    public String getHidingSpotDescription() {
        return String.format("In the %s of the %s", this.name, this.storageObjectName);
    }

    @Override
    public String getRoomName() {
        return roomName;
    }

    public void setStorageID(String storageID) {
        this.storageID = storageID;
    }
}
