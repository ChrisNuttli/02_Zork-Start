package ch.bbw.zork.Items;

import ch.bbw.zork.Container;
import ch.bbw.zork.interfaces.Storage;

import java.util.HashMap;

public class Backpack extends Item implements Storage {
    private Container contents;

    public Backpack() {
        super("Backpack", "A handy backpack with a lot of space");
        this.contents = new Container(30, 30, "backpack", "backpack", "");
    }

    @Override
    public HashMap<String, Item> getContents() {
        return this.contents.getContents();
    }

    @Override
    public void stashItem(Item item) {
        this.contents.stashItem(item);
    }

    @Override
    public Item fetchItem(String id) {
        return this.contents.fetchItem(id);
    }

    @Override
    public int getSpaceLimit() {
        return this.contents.getSpaceLimit();
    }

    @Override
    public double getWeightLimit() {
        return this.contents.getWeightLimit();
    }
}
