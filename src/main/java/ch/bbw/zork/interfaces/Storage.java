package ch.bbw.zork.interfaces;

import ch.bbw.zork.Items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public interface Storage {
    public HashMap<String, Item> getContents();
    public void stashItem(Item item);
    public Item fetchItem(String id);
    public int getSpaceLimit();
    public double getWeightLimit();
}
