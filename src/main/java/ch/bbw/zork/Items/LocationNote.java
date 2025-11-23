package ch.bbw.zork.Items;

import ch.bbw.zork.Game;
import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.interfaces.Hidden;
import ch.bbw.zork.interfaces.Uncover;

import java.util.UUID;

public class LocationNote extends Note implements Uncover {
    private final String uncoverID;
    private final String uncoverMessage;
    private boolean usedUncovered;

    public LocationNote(String text, String uncoverMessage) {
        super("A written reminder for one of the residents", text);
        this.uncoverID = UUID.randomUUID().toString();
        this.uncoverMessage = uncoverMessage;
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

    public static String getRandomText(FurnitureData concealingFurniture, Hidden hiddenFurniture) {
        int index = Game.getRandom().nextInt( 10);
        switch(index) {
            case 0:
                return String.format("Remember, the hidden %s can be found at %s.", hiddenFurniture.getName(), concealingFurniture.getHidingSpot());
            case 1:
                return String.format("Don’t forget that the concealed %s is placed at %s.", hiddenFurniture.getName(), concealingFurniture.getHidingSpot());
            case 2:
                return String.format("Just a reminder that the %s you’re looking for is hidden at %s.", hiddenFurniture.getName(), concealingFurniture.getHidingSpot());
            case 3:
                return String.format("In case you need it, the secret %s is stored at %s.", hiddenFurniture.getName(), concealingFurniture.getHidingSpot());
            case 4:
                return String.format("Take note: the missing %s is tucked away at %s.", hiddenFurniture.getName(), concealingFurniture.getHidingSpot());
            case 5:
                return String.format("For your reference, the hidden %s is located at %s.", hiddenFurniture.getName(), concealingFurniture.getHidingSpot());
            case 6:
                return String.format("Please remember that the concealed %s has been left at %s.", hiddenFurniture.getName(), concealingFurniture.getHidingSpot());
            case 7:
                return String.format("If you’re searching later, the %s is hidden at %s.", hiddenFurniture.getName(), concealingFurniture.getHidingSpot());
            case 8:
                return String.format("Make sure you recall that the %s is kept at %s.", hiddenFurniture.getName(), concealingFurniture.getHidingSpot());
            default:
                return String.format("Just so you know, the hidden %s remains at %s.", hiddenFurniture.getName(), concealingFurniture.getHidingSpot());
        }
    }
}
