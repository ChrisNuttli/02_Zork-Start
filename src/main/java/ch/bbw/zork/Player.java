package ch.bbw.zork;

import ch.bbw.zork.Items.Backpack;
import ch.bbw.zork.Items.Item;
import ch.bbw.zork.Items.Key;
import ch.bbw.zork.Items.Note;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.Uncover;

import java.util.*;

public class Player {
    private String name;
    private int x;
    private int y;
    private Container memory;
    private Backpack backpack;
    private Container leftHand;
    private Container rightHand;

    private Room currentRoom;
    private HashSet<Furniture> knownFurniture;
    private HashSet<Item> knownItems;

    public Player() {
        this.name = "";
        while (this.name.isEmpty()) {
            this.name = Zork2.getParser().promptInput("Enter your name: ");
        }

        this.memory = new Container(Integer.MAX_VALUE, 0, Integer.MAX_VALUE, "memory", "inventory", "");
        this.leftHand = new Container(Integer.MAX_VALUE, Double.MAX_VALUE, 1, "left hand", "inventory", "");
        this.rightHand = new Container(Integer.MAX_VALUE, Double.MAX_VALUE, 1, "right hand", "inventory", "");

        this.knownFurniture = new HashSet<>();
        this.knownItems = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    private void setBackpack(Backpack backpack) {
        if (this.backpack != null) {
            throw new RuntimeException("You are already wearing a backpack");
        }
        this.backpack = backpack;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    private Item dropLeftHandItem() {
        if (this.rightHand.getContents().isEmpty()) {
            throw new InputMismatchException("You do not hold any items in the left hand!");
        }
        int itemName = this.leftHand.getContents().keySet().iterator().next();
        Item item = this.leftHand.getContents().get(itemName);
        this.leftHand.getContents().remove(itemName);
        return item;
    }

    private Item dropRightHandItem() {
        if (this.rightHand.getContents().isEmpty()) {
            throw new InputMismatchException("You do not hold any items in the right hand!");
        }
        int itemName = this.rightHand.getContents().keySet().iterator().next();
        Item item = this.rightHand.getContents().get(itemName);
        this.rightHand.getContents().remove(itemName);
        return item;
    }

    private HashSet<Note> getAllNotes() {
        HashSet<Note> result = new HashSet<>();
        for (Item note : this.memory.getContents().values()) {
            if (note instanceof Note) {
                result.add((Note)note);
            }
        }

        return result;
    }

    public ArrayList<Item> getAllAvailableItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.addAll(memory.getContents().values());
        items.addAll(leftHand.getContents().values());
        items.addAll(rightHand.getContents().values());

        return items;
    }

    public ArrayList<Key> getAvailableKeys() {
        ArrayList<Key> keys = new ArrayList<>();
        for (Item item : this.getAllAvailableItems()) {
            if (item instanceof Key) {
                keys.add((Key) item);
            }
        }

        return keys;
    }

    public HashSet<Uncover> getAvailableUncovers() {
        HashSet<Uncover> uncovers = new HashSet<>();
        for (Item item : this.getAllNotes()) {
            if (item instanceof Uncover) {
                uncovers.add((Uncover) item);
            }
        }

        if (!this.leftHand.getContents().isEmpty()) {
            Item leftHandItem = this.leftHand.getContents().get(this.leftHand.getContents().keySet().iterator().next());
            if (leftHandItem instanceof Uncover) {
                uncovers.add((Uncover) leftHandItem);
            }
        }

        if (!this.rightHand.getContents().isEmpty()) {
            Item rightHandItem = this.rightHand.getContents().get(this.rightHand.getContents().keySet().iterator().next());
            if (rightHandItem instanceof Uncover) {
                uncovers.add((Uncover) rightHandItem);
            }
        }

        return uncovers;
    }

    public void printAllUncoveredFurnitureAndItems() {
        HashSet<Furniture> uncoveredFurniture = this.currentRoom.getUncoveredFurniture();
        System.out.println("Furniture:");
        uncoveredFurniture.forEach((furniture) -> {
            System.out.printf("%s:\t%s\n", furniture.getFurnitureID(), furniture.getName());
        });

        HashSet<Item> uncoveredItems = this.currentRoom.getUncoveredItems();
        System.out.println("\nItem:");
        uncoveredItems.forEach((item) -> {
            System.out.printf("%s:\t%s\n", item.getItemID(), item.getName());
        });
    }

    public Item getKnownItem(int itemID) {
        int i = 1;
        for (Item item : this.knownItems) {
            if (i == itemID) {
                return item;
            }
            i++;
        }

        throw new InputMismatchException("There is no item with this id");
    }

    public void move(Direction direction) {
        Door door = currentRoom.getDoor(direction);
        Room room = null;
        try {
            room = door.traverse(direction);
        }
        catch (RuntimeException e) {
            try {
                Item leftItem = leftHand.fetchItem();
                if (leftItem instanceof Key) {
                    door.tryUnlock((Key)leftItem);
                }
            }
            catch (Exception ignored) {

            }

            try {
                Item rightItem = rightHand.fetchItem();
                if (rightItem instanceof Key) {
                    door.tryUnlock((Key)rightItem);
                }
            }
            catch (Exception ignored) {
                System.out.println("error");
            }

            if (door.isLocked()) {
                throw e;
            }

            room = door.traverse(direction);
        }

        this.currentRoom = room;

        room.enter();
    }

    public void scan() {
        currentRoom.scan();
    }

    public void check(int furnitureID) {
        for (Furniture furniture : currentRoom.getUncoveredFurniture()) {
            if (furniture.getFurnitureID() == furnitureID) {
                System.out.printf("You inspect the %s closer\n", furniture.getName());

                furniture.check(this.getAvailableUncovers());
                HashSet<Item> checkedItems = furniture.getUncoveredItems();
                this.knownItems.addAll(checkedItems);
                if (checkedItems.isEmpty()) {
                    System.out.println("You did not find anything worthwhile");
                }
                else {
                    System.out.println("You found some items:");
                    printAllUncoveredFurnitureAndItems();
                }
                return;
            }
        }

        throw new InputMismatchException("No furniture with the given ID was found");
    }

    public String take(int itemID) {
        Item item = currentRoom.take(itemID);
        if (item == null) {
            return null;
        }

        if (item instanceof Backpack) {
            setBackpack((Backpack) item);
            return "back";
        }
        else if (item instanceof Note) {
            this.memory.stashItem(item);
            return "memory";
        }
        else {
            try {
                rightHand.stashItem(item);
                return "right hand";
            }
            catch(IllegalStateException e) {
                try {
                    leftHand.stashItem(item);
                    return "left hand";
                }
                catch(IllegalStateException ex) {
                    throw new RuntimeException("Cannot take anything while your hand are full.");
                }
            }
        }
    }

    public Item drop(String input) {
        switch (input.toLowerCase()) {
            case "left":
            case "l":
                return dropLeftHandItem();
            case "right":
            case "r":
                return dropRightHandItem();
            default:
                return dropItem(input);
        }
    }

    private Item dropItem(String item) {
        try {
            return dropLeftHandItem();
        }
        catch(InputMismatchException e) {
            try {
                return dropRightHandItem();
            }
            catch(InputMismatchException e2) {
                throw new InputMismatchException(String.format("You do not hold any items with the name '%s' in either hand!", item));
            }
        }
    }

    public void stash(String containerName) {
        if (backpack == null) {
            throw new IllegalStateException("You do not have a backpack to stash this item into");
        }

        Item item = null;

        switch(containerName) {
            case "left":
            case "l":
                item = this.leftHand.fetchItem();
                break;
            case "right":
            case "r":
                item = this.rightHand.fetchItem();
                break;
        }

        if (item == null) {
            throw new NullPointerException("Item cannot be stashed, since it was null");
        }

        this.backpack.stashItem(item);
    }

    public void fetch(Integer itemID) {
        if (this.leftHand.getContents().isEmpty() &&  this.rightHand.getContents().isEmpty()) {
            throw new IllegalStateException("You cannot fetch anything from the backpack if you have no free hand");
        }

        Item item = this.backpack.fetchItem(itemID);

        if (!this.rightHand.getContents().isEmpty()) {
            this.rightHand.stashItem(item);
        }
        else {
            this.leftHand.stashItem(item);
        }
    }

    public String getInventory() {
        StringBuilder result = new StringBuilder();
        if (!leftHand.getContents().isEmpty()) {
            result.append(String.format("Left Hand: %s\t\t\t", this.leftHand.fetchItem().getName()));
        }
        else {
            result.append(String.format("Left Hand: %s\t\t\t", "Empty"));
        }

        if (!rightHand.getContents().isEmpty()) {
            result.append(String.format("Right Hand: %s\n", this.rightHand.fetchItem().getName()));
        }
        else {
            result.append(String.format("Right Hand: %s\n", "Empty"));
        }

        if (backpack != null && !backpack.getContents().isEmpty()) {
            result.append("Backpack:\n\n");
            int i = 0;
            for (Item item : backpack.getContents().values()) {
                result.append(String.format("%s: %s\n", i, item.getName()));
                i++;
            }
        }

        if (!memory.getContents().isEmpty()) {
            result.append("Notes:\n\n");

            for (Item item : memory.getContents().values()) {
                Note note =  (Note) item;
                result.append(String.format("%s\n", note.getText()));
            }
        }

        result.append("\n");

        return result.toString();
    }
}
