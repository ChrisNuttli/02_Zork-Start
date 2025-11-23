package ch.bbw.zork.Items;

import ch.bbw.zork.interfaces.Unlock;

import java.util.HashMap;
import java.util.Set;

public class Crowbar extends Item implements Unlock {
    private final HashMap<String, String> unlockMessages;

    public Crowbar() {
        super("Crowbar", "A Handy tool for prying open doors, barrels and crates", 3, 5.0);
        this.unlockMessages = new HashMap<>();
    }

    @Override
    public Set<String> getLockIDs() {
        return this.unlockMessages.keySet();
    }

    @Override
    public void addUnlockMessage(String lockID, String unlockMessages) {
        this.unlockMessages.put(lockID, unlockMessages);
    }

    @Override
    public String getUnlockMessage(String lockID) {
        return this.unlockMessages.get(lockID);
    }
}
