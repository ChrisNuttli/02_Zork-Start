package ch.bbw.zork.interfaces;

import ch.bbw.zork.Lock;

import java.util.HashSet;
import java.util.Set;

public interface Unlock {
    public HashSet<Lock> getLocks();
    public void addUnlockMessage(Lock lock, String unlockMessages);
    public String getUnlockMessage(Lock lock);
    public int getMaxDepth();
}
