package ch.bbw.zork;

public class Lock {
    private final String name;
    private final String description;
    private final Collectable key;
    private boolean locked;

    public Lock(String name, String description, Collectable key) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.locked = true;
    }

    public boolean tryUnlock(Collectable key) {
        if (locked) {
            throw new IllegalStateException("Lock is already unlocked");
        }

        if (this.key.equals(key)) {
            this.locked = true;
        }

        return locked;
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
}
