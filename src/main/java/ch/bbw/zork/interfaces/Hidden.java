package ch.bbw.zork.interfaces;

import ch.bbw.zork.Container;
import ch.bbw.zork.Furniture;
import ch.bbw.zork.Items.LocationNote;
import ch.bbw.zork.enums.FurnitureData;

import java.util.ArrayList;
import java.util.HashSet;

public interface Hidden {
    public HashSet<Uncover> getUncoverItems();
    public void addUncoverItem(Uncover uncover);
    public boolean isUncovered();
    public void tryUncover(Uncover uncover);
    public void tryUncover(HashSet<Uncover> uncovers);
    public String getName();
    public void generateLocationNote(HidingSpot hidingSpot);
    public void setUncovered(boolean uncovered);
}
