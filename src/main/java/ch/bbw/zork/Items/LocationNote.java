package ch.bbw.zork.Items;

import ch.bbw.zork.Container;
import ch.bbw.zork.Furniture;
import ch.bbw.zork.Game;
import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.interfaces.Hidden;
import ch.bbw.zork.interfaces.HidingSpot;
import ch.bbw.zork.interfaces.Storage;
import ch.bbw.zork.interfaces.Uncover;

import java.util.UUID;

public class LocationNote extends Note implements Uncover {
    private final String[] locationTextTemplates = new String[]{
            "Remember, the hidden %s can be found %s.",
            "Don’t forget that the concealed %s is placed %s.",
            "Just a reminder that the %s you’re looking for is hidden %s.",
            "In case you need it, the secret %s is stored %s.",
            "Take note: the missing %s is tucked away %s.",
            "For your reference, the hidden %s is located %s.",
            "Please remember that the concealed %s has been left %s.",
            "If you’re searching later, the %s is hidden %s.",
            "Make sure you recall that the %s is kept %s.",
            "Just so you know, the hidden %s remains %s."
    };

    private final String[] uncoverMessageTemplates = new String[]{
            "You take a glance %s and spot a %s!",
    };

    private final String uncoverID;
    private final String uncoverMessage;
    private boolean usedUncovered;

    public LocationNote(String text, String uncoverMessage) {
        super("A written reminder for one of the residents", text);
        this.uncoverID = UUID.randomUUID().toString();
        this.uncoverMessage = uncoverMessage;
    }

    public LocationNote(HidingSpot hidingSpot, Hidden hiddenObject) {
        super("A written reminder for one of the residents");
        this.setText(getRandomText(hidingSpot, hiddenObject));
        this.uncoverID = UUID.randomUUID().toString();
        this.uncoverMessage = getUncoverMessage(hidingSpot, hiddenObject);
        this.setText(getRandomText(hidingSpot, hiddenObject));
    }

    @Override
    public String getUncoverID() {
        return this.uncoverID;
    }

    @Override
    public String getUncoverMessage() {
        return this.uncoverMessage;
    }

    public boolean getUsedUncover() {
        return this.usedUncovered;
    }

    public void setUsedUncover(boolean usedUncover) {
        this.usedUncovered = usedUncover;
    }

    private String getRandomText(HidingSpot hs, Hidden hiddenObject) {
        int index = Game.getRandom().nextInt(this.locationTextTemplates.length);
        return String.format(this.locationTextTemplates[index], hiddenObject.getName(), String.format("%s in the %s", hs.getHidingSpotDescription(), hs.getRoomName()));
    }

    private String getUncoverMessage(HidingSpot hs, Hidden hiddenObject) {
        int index = Game.getRandom().nextInt(this.uncoverMessageTemplates.length);
        return String.format(this.uncoverMessageTemplates[index], hs.getHidingSpotLabel(), hiddenObject.getName());
    }
}
