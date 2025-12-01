package ch.bbw.zork;

import ch.bbw.zork.Items.Key;
import ch.bbw.zork.enums.LockType;
import ch.bbw.zork.interfaces.Unlock;

import java.util.Objects;
import java.util.UUID;

public class Lock {
    private final String lockID;
    private final LockType lockType;
    private final String name;
    private final String description;
    private boolean locked;
    private Key key;
    private String code;

    public Lock(LockType lockType) {
        this.lockID = UUID.randomUUID().toString();
        this.lockType = lockType;
        this.name = lockType.name();
        this.description = lockType.getDescription();
        switch (lockType) {
            case KEY_HOLE:
                this.key = generateKey();
                break;
            case NUMPAD:
                this.code = generateCode();
                break;
        }

        this.locked = true;
    }

    private Key generateKey() {
        Key key = new Key();
        key.addUnlockMessage(this, "You turn the key and open the door successfully!");
        return key;
    }

    private String generateCode() {
        return String.format("%s", Game.getRandom().nextInt(9999));
    }

    public boolean tryUnlock(Unlock key) {
        if (!Objects.equals(this.lockType, LockType.KEY_HOLE)) {
            throw new RuntimeException("This lock cannot be unlocked using a key");
        }

        if (!locked) {
            return true;
        }

        if (this.key == key || key.getLocks().contains(this)) {
            this.locked = false;
        }

        return !locked;
    }

    public boolean tryUnlock(String code) {
        if (!Objects.equals(this.lockType, LockType.NUMPAD)) {
            throw new RuntimeException("This lock cannot be opened using a code!");
        }

        if (!locked) {
            return true;
        }

        if (this.code.equals(code)) {
            this.locked = false;
        }

        return !locked;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isLocked() {
        return locked;
    }

    public Key getKey() {
        return key;
    }

    public String getCode() {
        return this.code;
    }
}
