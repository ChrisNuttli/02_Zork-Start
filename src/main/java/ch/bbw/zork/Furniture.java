package ch.bbw.zork;

import ch.bbw.zork.Items.Item;
import ch.bbw.zork.Items.LocationNote;
import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.enums.LockType;
import ch.bbw.zork.interfaces.HidingSpot;
import ch.bbw.zork.interfaces.Uncover;
import ch.bbw.zork.interfaces.Hidden;

import java.util.*;

public class Furniture implements HidingSpot {
    private int furnitureID;
	private final String name;
	private final String description;
	private HashMap<String, Container> storage;
    private final FurnitureData furnitureData;
    private final String roomName;
    private boolean uncovered;
    private HashSet<Uncover> uncoverList;
    private Lock lock;
    private Safe safe;

    public Furniture(FurnitureData furnitureData, String roomName) {
        this.furnitureData = furnitureData;
        this.name = furnitureData.getName();
        this.description = furnitureData.getDescription();
        this.roomName = roomName;
        if (!furnitureData.getContainerNames().isEmpty()) {
            this.storage = new HashMap<>();
            for (String name : furnitureData.getContainerNames()) {
                Container newCont = new Container(name, this.name, roomName);
                this.storage.put(name.toLowerCase(), newCont);
            }
        }

        this.uncoverList = new HashSet<>();

        Game.getHouse().addFurniture(this);
    }

	public String getName() {
		return name;
	}

    public String getDescription() {
		return description;
	}

    public Container getContainer() {
        if (this.storage.size() > 1) {
            throw new InputMismatchException("This piece of furniture has more than one possible container to look through. Please enter a name to select a container");
        }

        if (this.storage.isEmpty()) {
            throw new InputMismatchException("This piece of furniture has no space for items");
        }

        return this.storage.get(this.storage.keySet().iterator().next());
    }

	public HashMap<String, Container> getStorage() {
        return this.storage;
    }

    public void setStorage(HashMap<String, Container> storage) {
        this.storage = storage;
    }

    public Lock getLock() {
        return lock;
    }

    public Lock generateLock(LockType lockType) {
        if (this.lock != null) {
            throw new IllegalStateException("lock already generated");
        }

        this.lock = new Lock(lockType);
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public FurnitureData getFurnitureData() {
        return furnitureData;
    }

    public HashSet<Item> getUncoveredItems() {
        HashSet<Item> result = new HashSet<>();
        if (this.storage != null) {
            for (Container container : storage.values()) {
                result.addAll(container.getCheckedItems());
            }
        }

        return result;
    }

    public void check(HashSet<Uncover> uncovers) {
        if (this.storage == null || this.storage.isEmpty()) {
            return;
        }

        String selectedStorage = "";
        Container container = null;

        if (this.storage.size() > 1) {
            System.out.printf("The %s has multiple parts that can be checked:\n", this.name);
            int i = 1;
            HashMap<Integer, Container> containersById = new HashMap<>();

            for (Container storage : this.storage.values()) {
                System.out.printf("%s:\t%s\n", i, storage.getName());
                containersById.put(i, storage);
                i++;
            }

            selectedStorage = Zork2.getParser().promptInput("\nWhich part would you like to check?\n");
            try {
                int selectedID = Integer.parseInt(selectedStorage);
                Container cont = containersById.get(selectedID);
                if (cont == null || cont.getContents().isEmpty()) {
                    System.out.printf("No option with id '%s' exists\n", selectedID);
                    return;
                }
                container = cont;
            }
            catch (NumberFormatException ignored) {
                if (!storage.containsKey(selectedStorage.toLowerCase())) {
                    System.out.printf("Option '%s' does not exist\n", selectedStorage);
                    return;
                }

                container = this.storage.get(selectedStorage);
            }
        }
        else {
            selectedStorage = this.storage.keySet().iterator().next();
            container = this.storage.get(selectedStorage);
        }

        container.check(uncovers);
    }

    private ArrayList<Container> getContainersShuffled() {
        ArrayList<Container> containers = new ArrayList<>();
        while (containers.size() < this.storage.size())  {
            int randomInt = Game.getRandom().nextInt(storage.size());
            int i = 0;
            for (Container container : this.storage.values()) {
                if (randomInt == i) {
                    if (!containers.contains(container)) {
                        containers.add(container);
                    }
                    break;
                }
                i++;
            }
        }

        return containers;
    }

    public void hideItem(Hidden hiddenItem) {
        if (this.storage.isEmpty()) {
            throw new IllegalStateException("Cannot hide an Item. This Furniture does not have any storage");
        }

        boolean success = false;
        for (Container container : this.getContainersShuffled()) {
            success = true;
            try {
                container.stashItem((Item)hiddenItem);
                hiddenItem.generateLocationNote(container);
                break;
            }
            catch(Exception e) {
                success = false;
                container.removeItem((Item)hiddenItem);
            }
        }

        if (!success) {
            throw new RuntimeException("Cannot hide Item");
        }
    }

    public void hideSafe() {
        String hidingSpot = this.furnitureData.getHidingSpot();
        if (Objects.equals(hidingSpot, "")) {
            throw new RuntimeException("No hiding spot for safe found");
        }
        this.safe = new Safe(roomName);
        this.generateLocationNote(safe);
    }

    @Override
    public void generateLocationNote(Hidden hiddenObject) {
        if (hiddenObject instanceof Item) {
            throw new InputMismatchException("Only The Safe can be hidden in furniture.");
        }

        new LocationNote(this, hiddenObject);
    }

    @Override
    public void tryUncoverHiddenItems(Uncover uncover) {
        // TODO: Implement Method
    }

    @Override
    public String getHidingSpotLabel() {
        return this.furnitureData.getHidingSpot();
    }

    @Override
    public String getRoomName() {
        return this.roomName;
    }

    @Override
    public String getHidingSpotDescription() {
        return this.furnitureData.getHidingSpot();
    }

    public int getFurnitureID() {
        return furnitureID;
    }

    public void setFurnitureID(int furnitureID) {
        this.furnitureID = furnitureID;
    }

    public boolean isUncovered() {
        return uncovered;
    }

    public void setUncovered(boolean uncovered) {
        this.uncovered = uncovered;
    }

    public void addUncoverItem(Uncover uncoverItem) {
        this.uncoverList.add(uncoverItem);
    }

    public void tryUncover(HashSet<Uncover> uncovers) {
        if (this.uncoverList.isEmpty()) {
            this.uncovered = true;
        }
        else {
            for (Uncover uncover : uncovers) {
                if (uncoverList.contains(uncover)) {
                    this.uncovered = true;
                    return;
                }
            }
        }
    }
}
