package ch.bbw.zork.Items;

import ch.bbw.zork.interfaces.Uncover;

import java.util.UUID;

public class Flashlight extends Item implements Uncover {
    public final String uncoverID;
    public final String uncoverMessage;

    public Flashlight() {
        super("Flashlight", "An average flashlight to illuminate dark spots");
        this.uncoverID = UUID.randomUUID().toString();
        this.uncoverMessage = "You shine the Flashlight in a dark corner and discover something hidden!";
    }

    @Override
    public String getUncoverID() {
        return this.uncoverID;
    }

    @Override
    public String getUncoverMessage() {
        return this.uncoverMessage;
    }
}
