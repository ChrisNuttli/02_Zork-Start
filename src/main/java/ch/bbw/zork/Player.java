package ch.bbw.zork;

import ch.bbw.zork.Items.Backpack;
import ch.bbw.zork.Items.Item;
import ch.bbw.zork.Items.Key;
import ch.bbw.zork.interfaces.Uncover;

import java.util.ArrayList;
import java.util.Collection;

public class Player {
    private String name;
    private int x;
    private int y;
    private Container memory;
    private Backpack backpack;
    private Container leftHand;
    private Container rightHand;

    public Player() {
        this.name = "";
        while (this.name.equals("")) {
            this.name = Zork2.parser.promptInput("Enter your name: ");
        }

        this.memory = new Container(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        this.leftHand = new Container(Integer.MAX_VALUE, Double.MAX_VALUE, 1);
        this.rightHand = new Container(Integer.MAX_VALUE, Double.MAX_VALUE, 1);
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

    public ArrayList<Item> getAllAvailableItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.addAll((Collection<? extends Item>) memory.getContents());
        items.addAll((Collection<? extends Item>) leftHand.getContents());
        items.addAll((Collection<? extends Item>) rightHand.getContents());

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
}
