package ch.bbw.zork;

import ch.bbw.zork.Items.LocationNote;
import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.interfaces.Hidden;
import ch.bbw.zork.interfaces.Uncover;

import java.util.ArrayList;

public class Safe extends Furniture implements Hidden {
    public Safe() {
        super(FurnitureData.SAFE);
    }

    @Override
    public ArrayList<String> getUncoverIDs() {
        return null;
    }

    @Override
    public void addUncoverID(String uncoverID) {

    }

    @Override
    public boolean getIsUncovered() {
        return false;
    }

    @Override
    public void setIsUncovered(boolean isUncovered) {

    }

    @Override
    public boolean tryUncover(String uncoverID) {
        return false;
    }

    @Override
    public boolean tryUncover(ArrayList<Uncover> uncovers) {
        return false;
    }

    @Override
    public LocationNote generatLocationNote(FurnitureData hidingSpot) {
        return null;
    }
}
