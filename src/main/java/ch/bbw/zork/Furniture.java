package ch.bbw.zork;

import ch.bbw.zork.Items.Item;
import ch.bbw.zork.Items.LocationNote;
import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.enums.LockType;
import ch.bbw.zork.interfaces.Hidden;
import ch.bbw.zork.interfaces.Uncover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Furniture implements Hidden {
    private final String furnitureID;
	private final String name;
	private final String description;
	private HashMap<String, Container> storage;
    private HashMap<Integer, Item> checkedItemsList;
    private final FurnitureData furnitureData;
    private final ArrayList<String> uncoverIDs;
    private boolean uncovered;
    private Lock lock;

    public Furniture(FurnitureData furnitureData) {
        this.furnitureID = UUID.randomUUID().toString();
        this.furnitureData = furnitureData;
        this.name = furnitureData.getName();
        this.description = furnitureData.getDescription();
        if (!furnitureData.getContainerNames().isEmpty()) {
            this.storage = new HashMap<>();
            for (String name : furnitureData.getContainerNames()) {
                this.storage.put(name, new Container());
            }
        }
        this.uncoverIDs = new ArrayList<>();
    }

	public String getName() {
		return name;
	}

    @Override
    public LocationNote generatLocationNote(FurnitureData hidingSpot) {
        return new LocationNote("", LocationNote.getRandomText(hidingSpot, this));
    }

    public String getDescription() {
		return description;
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

    public String getFurnitureID() {
        return furnitureID;
    }

    public FurnitureData getFurnitureData() {
        return furnitureData;
    }

    @Override
    public ArrayList<String> getUncoverIDs() {
        return this.uncoverIDs;
    }

    @Override
    public void addUncoverID(String uncoverID) {
        this.uncoverIDs.add(uncoverID);
        this.uncovered = true;
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

    public void check() {
        if (this.storage.isEmpty()) {
            System.err.println("Nothing to see here");
            return;
        }

        String selectedStorage = "";
        if (this.storage.keySet().size() > 1) {
            System.out.printf("The %s has multiple parts that can be checked:\n", this.name);
            for (String storage : this.storage.keySet()) {
                System.out.println(storage);
            }

            selectedStorage = Game.getParser().promptInput("Which part would you like to check?");
            while (!storage.containsKey(selectedStorage)) {
                System.out.printf("Option %s does not exist\n", selectedStorage);
                selectedStorage = Game.getParser().promptInput("Which part would you like to check?");
            }
        }
        else {
            selectedStorage = this.storage.keySet().iterator().next();
        }

        Container container = this.storage.get(selectedStorage);
        if (container.getContents().isEmpty()) {
            System.out.println("There is nothing to be found");
            return;
        }

        System.out.println("Found some items:");

        Player player = Game.getPlayer();

        HashMap<Integer, Item> itemList = new HashMap<>();
        int i = 0;
        for (String itemName : container.getContents().keySet()) {
            Item item = container.getContents().get(itemName);
            if (!item.getIsUncovered()) {
                for (Uncover uncover : player.getAllUncovers()) {
                    if (item.tryUncover(uncover.getUncoverID())) {
                        break;
                    }
                }
            }

            if (item.getIsUncovered()) {
                System.out.printf("%s: %s", i, itemName);
                itemList.put(i++, item);
            }
        }

        Game.getParser().setLoadedItemList(itemList);
    }
}
