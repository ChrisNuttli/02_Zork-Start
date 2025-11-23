package ch.bbw.zork.interfaces;

import ch.bbw.zork.Items.LocationNote;
import ch.bbw.zork.enums.FurnitureData;

import java.util.ArrayList;

public interface Hidden {
    public ArrayList<String> getUncoverIDs();
    public void addUncoverID(String uncoverID);
    public boolean getIsUncovered();
    public void setIsUncovered(boolean isUncovered);
    public boolean tryUncover(String uncoverID);
    public String getName();
    public LocationNote generatLocationNote(FurnitureData hidingSpot);
}
