package ch.bbw.zork;

import ch.bbw.zork.Items.LocationNote;
import ch.bbw.zork.enums.FurnitureData;
import ch.bbw.zork.enums.LockType;
import ch.bbw.zork.interfaces.Hidden;
import ch.bbw.zork.interfaces.HidingSpot;
import ch.bbw.zork.interfaces.Uncover;

import java.util.ArrayList;
import java.util.HashSet;

public class Safe extends Furniture implements Hidden {
    private Lock numPadLock;
    private Lock keyHoleLock;

    private HashSet<Uncover> uncoverItems;
    private boolean uncovered;

    public Safe(String roomName) {
        super(FurnitureData.SAFE, roomName);

        this.numPadLock = new Lock(LockType.NUMPAD);
        this.keyHoleLock = new Lock(LockType.KEY_HOLE);
        this.uncoverItems = new HashSet<>();
        this.uncovered = false;
    }

    @Override
    public HashSet<Uncover> getUncoverItems() {
        return uncoverItems;
    }

    @Override
    public void addUncoverItem(Uncover uncover) {
        this.uncoverItems.add(uncover);
    }

    @Override
    public boolean isUncovered() {
        return uncovered;
    }

    @Override
    public void tryUncover(Uncover uncover) {
        if (this.isUncovered()) {
            return;
        }

        if (this.uncoverItems.contains(uncover)) {
            this.uncovered = true;
        }
    }

    @Override
    public void tryUncover(HashSet<Uncover> uncovers) {
        for (Uncover uncover : uncovers) {
            if (this.isUncovered()) {
                return;
            }
            this.tryUncover(uncover);
        }
    }

    @Override
    public void generateLocationNote(HidingSpot hidingSpot) {
        if (hidingSpot instanceof Container) {
            throw new UnsupportedOperationException("Cannot hide safe in container!");
        }

        hidingSpot.generateLocationNote(this);
    }
}
