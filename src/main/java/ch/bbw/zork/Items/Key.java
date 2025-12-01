package ch.bbw.zork.Items;

import ch.bbw.zork.Lock;
import ch.bbw.zork.interfaces.Unlock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Key extends Item implements Unlock {
    private HashMap<Lock, String> unlockMessages;

    public Key() {
        super("Key", "A small metal key that looks like it could open a lock.", 1, 0.2);
        this.unlockMessages = new HashMap<>();
    }

    @Override
    public HashSet<Lock> getLocks() {
        return new HashSet<>(this.unlockMessages.keySet());
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
