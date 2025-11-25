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

        this.memory = new Container(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        this.leftHand = new Container(Integer.MAX_VALUE, Double.MAX_VALUE, 1);
        this.rightHand = new Container(Integer.MAX_VALUE, Double.MAX_VALUE, 1);

        this.knownFurniture = new HashSet<>();
        this.knownItems = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    private void setBackpack(Backpack backpack) {
        if (this.backpack != null) {
            throw new RuntimeException("You are already wearing a backpack");
        }
        this.backpack = backpack;
    }

    public HashSet<Furniture> getKnownFurniture() {
        return knownFurniture;
    }

    public void setKnownFurniture(HashSet<Furniture> knownFurniture) {
        this.knownFurniture = knownFurniture;
    }

    public HashSet<Item> getKnownItems() {
        return knownItems;
    }

    public void setKnownItems(HashSet<Item> knownItems) {
        this.knownItems = knownItems;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
        currentRoom.enter();
    }

    public void spawn(Room spawnRoom) {
        spawnRoom.firstEnter();
        this.currentRoom = spawnRoom;
    }

    private Item dropLeftHandItem() {
        if (this.rightHand.getContents().isEmpty()) {
            throw new InputMismatchException("You do not hold any items in the left hand!");
        }
        String itemName = this.leftHand.getContents().keySet().iterator().next();
        Item item = this.leftHand.getContents().get(itemName);
        this.leftHand.getContents().remove(itemName);
        return item;
    }

    private Item dropRightHandItem() {
        if (this.rightHand.getContents().isEmpty()) {
            throw new InputMismatchException("You do not hold any items in the right hand!");
        }
        String itemName = this.rightHand.getContents().keySet().iterator().next();
        Item item = this.rightHand.getContents().get(itemName);
        this.rightHand.getContents().remove(itemName);
        return item;
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

    public ArrayList<Uncover> getAllUncovers() {
        ArrayList<Uncover> uncovers = new ArrayList<>();
        for (Item item : this.getAllAvailableItems()) {
            if (item instanceof Uncover) {
                uncovers.add((Uncover) item);
            }
        }
        return uncovers;
    }

    public void printAllFurnitureAndItems() {
        if (this.knownFurniture == null) {
            throw new RuntimeException("You have not yet scanned this room.");
        }

        if (this.knownFurniture.isEmpty()) {
            throw new RuntimeException("There is nothing to be found");
        }

        System.out.println("Furniture:");
        int i = 1;
        for (Furniture furniture : this.knownFurniture) {
            System.out.printf("%s: %s\n", i, furniture.getName());
            i++;
        }

        System.out.println("Items:");
        i = 1;
        for (Item item : this.knownItems) {
            System.out.printf("%s: %s\n", i, item.getName());
            i++;
        }
    }

    private Item getKnownItem(int itemID) {
        int i = 1;
        for (Item item : this.knownItems) {
            if (i == itemID) {
                return item;
            }
        }

        throw new InputMismatchException("There is no item with this id");
    }

    public void move(Direction direction) {
        Door door = currentRoom.getDoor(direction);
        Room room = door.traverse(direction);
        this.currentRoom = room;
        this.setX(room.getX());
        this.setY(room.getY());

        room.enter();
    }

    public void scan() {
        currentRoom.scan();
        this.knownFurniture = currentRoom.getScannedFurniture();
        this.knownItems = currentRoom.getScannedFloorItems();
    }

    public void check(String furnitureID) {
        int i = 1;
        for (Furniture furniture : this.knownFurniture) {
            if (String.valueOf(i).equals(furnitureID)) {
                furniture.check(this.getAllUncovers());
                this.knownItems.addAll(furniture.getCheckedItems());
                return;
            }
            i++;
        }

        throw new InputMismatchException("No furniture with the given ID was found");
    }

    public String take(int itemID) {
        Item item = getKnownItem(itemID);

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
                return "right";
            }
            catch(IllegalStateException e) {
                try {
                    leftHand.stashItem(item);
                    return "left";
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

    public void fetch(String itemID) {
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
}
