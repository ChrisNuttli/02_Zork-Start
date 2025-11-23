package ch.bbw.zork.interfaces;

import java.util.Set;

public interface Unlock {
    public Set<String> getLockIDs();
    public void addUnlockMessage(String lockID, String unlockMessages);
    public String getUnlockMessage(String lockID);
}
