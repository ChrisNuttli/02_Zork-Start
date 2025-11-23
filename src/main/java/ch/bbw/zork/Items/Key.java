package ch.bbw.zork.Items;

import ch.bbw.zork.interfaces.Unlock;

import java.util.HashMap;
import java.util.Set;

public class Key extends Item implements Unlock {
    private HashMap<String, String> unlockMessages;
    private boolean usedUnlock;

    public Key(String lockID) {
        super("Key", "A small metal key that looks like it could open a lock.", 1, 0.2);
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

    public boolean getUsedUnlock() {
        return this.usedUnlock;
    }

    public void setUsedUnlock(boolean usedUnlock) {
        this.usedUnlock = usedUnlock;
    }
}
