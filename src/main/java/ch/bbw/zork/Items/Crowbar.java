package ch.bbw.zork.Items;

import ch.bbw.zork.Lock;
import ch.bbw.zork.interfaces.Unlock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Crowbar extends Item implements Unlock {
    private final HashMap<Lock, String> unlockMessages;

    public Crowbar() {
        super("Crowbar", "A Handy tool for prying open doors, barrels and crates", 3, 5.0);
        this.unlockMessages = new HashMap<>();
    }

    @Override
    public HashSet<Lock> getLocks() {
        return null;
    }

    @Override
    public void addUnlockMessage(Lock lock, String unlockMessages) {
        this.unlockMessages.put(lock, unlockMessages);
    }

    @Override
    public String getUnlockMessage(Lock lock) {
        return this.unlockMessages.get(lock);
    }

    @Override
    public int getMaxDepth() {
        return 0;
    }
}
