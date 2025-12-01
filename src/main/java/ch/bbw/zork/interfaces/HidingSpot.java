package ch.bbw.zork.interfaces;

import ch.bbw.zork.Items.LocationNote;

public interface HidingSpot {
    public void generateLocationNote(Hidden hiddenObject);
    public void tryUncoverHiddenItems(Uncover uncover);
    public String getHidingSpotLabel();
    public String getHidingSpotDescription();
    public String getRoomName();
}
