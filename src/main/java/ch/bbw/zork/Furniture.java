package ch.bbw.zork;

import ch.bbw.zork.Items.Item;
import ch.bbw.zork.Items.LocationNote;
import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.enums.LockType;
import ch.bbw.zork.interfaces.Hidden;
import ch.bbw.zork.interfaces.Uncover;

import java.util.*;

public class Furniture {
    private final String furnitureID;
	private final String name;
	private final String description;
	private HashMap<String, Container> storage;
    private final FurnitureData furnitureData;
    private final ArrayList<String> uncoverIDs;
    private boolean uncovered;
    private Lock lock;
    private HashSet<Item> checkedItems;

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
        this.checkedItems = new HashSet<>();

        Game.getHouse().addFurniture(this);
    }

	public String getName() {
		return name;
	}

    public String getDescription() {
		return description;
	}

    public Container getContainer(String containerName) {
        return this.storage.get(containerName);
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

    public String getFurnitureID() {
        return furnitureID;
    }

    public FurnitureData getFurnitureData() {
        return furnitureData;
    }

    public HashSet<Item> getCheckedItems() {
        return checkedItems;
    }

    public void check(ArrayList<Uncover> uncovers) {
        if (this.storage.isEmpty()) {
            System.err.println("Nothing to see here");
            return;
        }

        String selectedStorage = "";
        if (this.storage.size() > 1) {
            System.out.printf("The %s has multiple parts that can be checked:\n", this.name);
            for (String storage : this.storage.keySet()) {
                System.out.println(storage);
            }

            selectedStorage = Zork2.getParser().promptInput("Which part would you like to check?");
            while (!storage.containsKey(selectedStorage)) {
                System.out.printf("Option %s does not exist\n", selectedStorage);
                selectedStorage = Zork2.getParser().promptInput("Which part would you like to check?");
            }
        }
        else {
            selectedStorage = this.storage.keySet().iterator().next();
        }

        Container container = this.storage.get(selectedStorage);
        container.check(uncovers);
        if (container.getCheckedItems().isEmpty()) {
            System.out.println("There is nothing to be found");
            return;
        }

        this.checkedItems.addAll(container.getCheckedItems());


//        System.out.println("Found some items:");

//        Player player = Game.getPlayer();
//
//        HashMap<Integer, Item> itemList = new HashMap<>();
//        int i = 0;
//        for (String itemName : container.getContents().keySet()) {
//            Item item = container.getContents().get(itemName);
//            if (!item.getIsUncovered()) {
//                for (Uncover uncover : player.getAllUncovers()) {
//                    if (item.tryUncover(uncover.getUncoverID())) {
//                        break;
//                    }
//                }
//            }
//
//            if (item.getIsUncovered()) {
//                System.out.printf("%s: %s", i, itemName);
//                itemList.put(i++, item);
//            }
//        }
//
//        Game.getParser().setLoadedItemList(itemList);
    }
}
